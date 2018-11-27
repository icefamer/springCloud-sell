package com.icefamer.productserver.repository;

import com.icefamer.productserver.ProductApplicationTests;
import com.icefamer.productserver.dataobject.ProductInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInfoRepositoryTest extends ProductApplicationTests {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findAllByProductStatus() {
        List<ProductInfo> list = productInfoRepository.findAllByProductStatus(0);
        System.out.println(list.get(0));
    }
}