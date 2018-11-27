package com.icefamer.productserver.controller;

import com.icefamer.productcommon.DecreaseStockInput;
import com.icefamer.productserver.dataobject.ProductCategory;
import com.icefamer.productserver.dataobject.ProductInfo;
import com.icefamer.productserver.service.CategoryService;
import com.icefamer.productserver.service.ProductService;
import com.icefamer.productserver.utils.ResultVOUtil;
import com.icefamer.productserver.vo.ProductInfoVO;
import com.icefamer.productserver.vo.ProductVO;
import com.icefamer.productserver.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    /*
    * 1、查询所有在架的商品
    * 2、获取类目type列表
    * 3、查询类目
    * 4、构造数据
    */
    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        //1、查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2、获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //3、从数据库查询类目
        List<ProductCategory> productCategoryList = categoryService.findAllInCategoryType(categoryTypeList);
        //4、构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo :
                    productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表（给订单服务用）
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findListByProductIds(productIdList);
    }

    /**
     * 扣库存（给订单服务用）
     * @param cartDTOList
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }
}
