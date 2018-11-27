package com.icefamer.productserver.service;

import com.icefamer.productcommon.DecreaseStockInput;
import com.icefamer.productserver.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {
    /*
    * 查询所有在架商品列表
    * */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表，给订单服务调用
     * @param productIdList
     * @return
     */
    List<ProductInfo> findListByProductIds(List<String> productIdList);

    /**
     * 扣库存
     * @param cartDTOList
     */
    void decreaseStock(List<DecreaseStockInput> cartDTOList);
}
