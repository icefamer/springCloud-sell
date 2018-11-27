package com.icefamer.orderserver.service.impl;

import com.icefamer.orderserver.dataobject.OrderDetail;
import com.icefamer.orderserver.dataobject.OrderMaster;
import com.icefamer.orderserver.dto.OrderDTO;
import com.icefamer.orderserver.enums.OrderStatus;
import com.icefamer.orderserver.enums.PayStatus;
import com.icefamer.orderserver.repository.OrderDetailRepository;
import com.icefamer.orderserver.repository.OrderMasterRepository;
import com.icefamer.orderserver.service.OrderService;
import com.icefamer.orderserver.utils.IdBuilder;
import com.icefamer.productclient.ProductClient;
import com.icefamer.productcommon.DecreaseStockInput;
import com.icefamer.productcommon.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = IdBuilder.orderIdBuild();
        Integer detailIncrease = 1;
        //查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);
        //计算总价
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail :
                orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo :
                    productInfoList) {
                if (orderDetail.getProductId().equals(productInfo.getProductId())){
                    amount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(amount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    if (detailIncrease < 10){
                        orderDetail.setDetailId(orderId + "00" + detailIncrease);
                    }else if (detailIncrease < 100){
                        orderDetail.setDetailId(orderId + "0" + detailIncrease);
                    }else{
                        orderDetail.setDetailId(orderId + detailIncrease);
                    }
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                    //订单详情编号追加码自增
                    detailIncrease++;
                }
            }
        }
        //扣库存（调用商品服务）
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderStatus(OrderStatus.NEW.getCode());
        orderMaster.setPayStatus(PayStatus.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
