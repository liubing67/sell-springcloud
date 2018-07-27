package com.abing.order.controller;

import com.abing.product.DTO.CartDTO;
import com.abing.product.client.ProductClient;
import com.abing.product.dataobject.DecreaseStockInput;
import com.abing.product.dataobject.ProductInfo;
import com.abing.product.dataobject.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//@RestController
//@Slf4j
public class ClientController {

//    @Autowired
//    private ProductClient productClient;
//
//
////    2.第二种方式
////    @Autowired
////    private LoadBalancerClient loadBalancerClient;
//
//    //3，第三种方式
//    @Autowired
//    private RestTemplate restTemplate;
//    @GetMapping("/getProductMsg")
//    public String getProductMsg(){
//        //1.第一种方式 （直接中RestTemplate，url写死）     缺点：ip写死 ；如果product服务做负载均衡（多台服务运行时）无法进行全部访问
////        RestTemplate restTemplate=new RestTemplate();
////        String response=restTemplate.getForObject("http://localhost:8080/msg",String.class);
//
//        //2.第二种方式 （利用loadBalancerClient通过应用名获取url，然后再使用RestTemplate）
////        ServiceInstance serviceInstance=loadBalancerClient.choose("PRODUCT");
////        String url=String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"msg";
////        RestTemplate restTemplate=new RestTemplate();
////        String response=restTemplate.getForObject(url,String.class);
//
//        //3，第三种方式(利用@LoadBalance,可在restTemplate里使用名字)
//        String response=restTemplate.getForObject("http://PRODUCT/msg",String.class);
//        log.info("response={}",response);
//        return response;
//    }
//
//    @GetMapping("/getProductList")
//    public String getProductList(){
//        List<ProductInfoOutput> productInfoList=productClient.listForOrder(Arrays.asList("1"));
//        return productInfoList.toString();
//    }
//
//    @GetMapping("/product/productDecreaseStock")
//    public String productDecreaseStock(){
//        productClient.decreaseStock(Arrays.asList(new DecreaseStockInput("1",1)));
//        return "ok";
//    }
}
