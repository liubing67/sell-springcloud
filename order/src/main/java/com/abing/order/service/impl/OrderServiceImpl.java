package com.abing.order.service.impl;

import com.abing.order.dataobject.OrderDetail;
import com.abing.order.dataobject.OrderMaster;
import com.abing.order.dto.OrderDto;
import com.abing.order.enums.OrderStatusEnum;
import com.abing.order.enums.PayStatusEnum;
import com.abing.order.repository.OrderDetailRepository;
import com.abing.order.repository.OrderMasterRepository;
import com.abing.order.service.OrderService;
import com.abing.order.utils.KeyUtil;
import com.abing.product.client.ProductClient;
import com.abing.product.dataobject.DecreaseStockInput;
import com.abing.product.dataobject.ProductInfo;
import com.abing.product.dataobject.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;
    @Override
    public OrderDto create(OrderDto orderDto) {

        String orderId=KeyUtil.genUniqueKey();
        //查询商品信息（调用商品服务）
        List<String> productIdList=orderDto.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList=productClient.listForOrder(productIdList);
        //计算总价
        BigDecimal orderAmout=new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()){
            for (ProductInfoOutput productInfo:productInfoList){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    //单价*数量
                    orderAmout=productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //扣库存
        List<DecreaseStockInput> decreaseStockInputList=orderDto.getOrderDetailList().stream()
                .map(e->new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return null;
    }
}
