package com.abing.order.service.impl;

import com.abing.order.dataobject.OrderMaster;
import com.abing.order.dto.OrderDto;
import com.abing.order.enums.OrderStatusEnum;
import com.abing.order.enums.PayStatusEnum;
import com.abing.order.repository.OrderDetailRepository;
import com.abing.order.repository.OrderMasterRepository;
import com.abing.order.service.OrderService;
import com.abing.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public OrderDto create(OrderDto orderDto) {

        //查询商品信息（调用商品服务）

        //计算总价

        //扣库存


        //订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDto.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(11));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return null;
    }
}
