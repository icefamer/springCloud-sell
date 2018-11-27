package com.icefamer.productserver.service.impl;

import com.icefamer.productserver.dataobject.ProductCategory;
import com.icefamer.productserver.repository.ProductCategoryRepository;
import com.icefamer.productserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public List<ProductCategory> findAllInCategoryType(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
