package com.abing.product.controller;

import com.abing.product.DTO.CartDTO;
import com.abing.product.VO.ProductInfoVO;
import com.abing.product.VO.ProductVO;
import com.abing.product.VO.ResultVO;
import com.abing.product.dataobject.ProductCategory;
import com.abing.product.dataobject.ProductInfo;
import com.abing.product.dataobject.ProductInfoOutput;
import com.abing.product.service.ProductCategoryService;
import com.abing.product.service.ProductService;
import com.abing.product.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 1.查询所有在架的商品
     * 2.获取来自type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        //获取所有上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //获取所有上架商品的catetype
        List<Integer> categoryTypeList=productInfoList.stream().
                map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //根据catetype获取商品类目
        List<ProductCategory> productCategoryList=productCategoryService.findByCategoryTypeIn(categoryTypeList);

       //构造数据
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
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
     * 获取商品列表（给订单服务专用）
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }
}
