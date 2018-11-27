package com.icefamer.orderserver.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.icefamer.orderserver.utils.JsonUtil;
import com.icefamer.productcommon.ProductInfoOutput;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //message => ProductInfoOutput
        List<ProductInfoOutput> productInfoOutputList = JsonUtil.toObject(message,
                new TypeReference<List<ProductInfoOutput>>() {});

        //储存到redis中
        for (ProductInfoOutput productInfoOutput :
                productInfoOutputList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId())
                    , String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
