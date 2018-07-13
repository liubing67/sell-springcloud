package com.abing.product.service;

import com.abing.product.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 查询所有商品类型
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
