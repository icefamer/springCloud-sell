package com.icefamer.productserver.service.impl;

import com.icefamer.productcommon.DecreaseStockInput;
import com.icefamer.productcommon.ProductInfoOutput;
import com.icefamer.productserver.dataobject.ProductInfo;
import com.icefamer.productserver.enums.ProductStatusEnum;
import com.icefamer.productserver.enums.ResultEnum;
import com.icefamer.productserver.exception.ProductException;
import com.icefamer.productserver.repository.ProductCategoryRepository;
import com.icefamer.productserver.repository.ProductInfoRepository;
import com.icefamer.productserver.service.ProductService;
import com.icefamer.productserver.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findAllByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findListByProductIds(List<String> productIdList) {
        return productInfoRepository.findAllByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
        //发送mq消息
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> DecreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput DecreaseStockInput :
                DecreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(DecreaseStockInput.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存是否足够
            ProductInfo productInfo = productInfoOptional.get();
            Integer result = productInfo.getProductStock() - DecreaseStockInput.getProductQuantity();
            if (result < 0){
                throw  new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
