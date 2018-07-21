package com.abing.product.service;

import com.abing.product.DTO.CartDTO;
import com.abing.product.dataobject.DecreaseStockInput;
import com.abing.product.dataobject.ProductInfo;
import com.abing.product.dataobject.ProductInfoOutput;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品
     */
    List<ProductInfo> findUpAll();

    /**
     *  查询商品列表
     * @param productIdList
     * @return
     */
   List<ProductInfoOutput> findList(List<String> productIdList);


    /**
     * 扣库存
     * @param cartDTOList
     */
   void decreaseStock(List<DecreaseStockInput> cartDTOList);
}
