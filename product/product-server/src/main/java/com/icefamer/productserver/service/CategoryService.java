package com.icefamer.productserver.service;

import com.icefamer.productserver.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> findAllInCategoryType(List<Integer> categoryTypeList);
}
