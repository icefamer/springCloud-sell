package com.icefamer.productserver.repository;

import com.icefamer.productserver.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory save(ProductCategory productCategory);
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
