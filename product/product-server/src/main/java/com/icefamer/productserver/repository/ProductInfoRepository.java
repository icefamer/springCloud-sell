package com.icefamer.productserver.repository;

import com.icefamer.productserver.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findAllByProductStatus(Integer productStatus);
    List<ProductInfo> findAllByProductIdIn(List<String> productIdList);
}
