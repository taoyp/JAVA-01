package com.lxtyp.basic.order.service;

import com.google.inject.internal.util.Lists;
import com.lxtyp.basic.generator.dao.OrderMapper;
import com.lxtyp.basic.generator.entity.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @ClassName OrderService
 * @Date 2021/3/5 18:34
 * @Description TODO
 * @Status ISFINISH
 */
@Service
public class OrderService {

  @Autowired
  private OrderMapper orderMapper;

  /**
   * 新增订单入表
   * */
  public void orderIn() {
    BigDecimal bigDecimal = new BigDecimal(10);
    Date date = new Date();
    List<Order> list = new ArrayList<>();
    for (long i = 0; i < 1000; i++) {
      Order order = new Order();
      order.setORDER_ID(i);
      order.setORDER_ADDRESS("in Xian Now");
      order.setORDER_PRICE(bigDecimal);
      order.setORDER_PAY(bigDecimal);
      order.setORDER_DISCOUNT(bigDecimal);
      order.setSTATUS("0");
      order.setUSER_ID((long)(Math.random()*1000000L));
      order.setPAY_TYPE("0");
      order.setCREATE_TIME(date);
      order.setUPDATE_TIME(date);

      list.add(order);
    }
    orderMapper.insert(list);
  }

  /**
   * 删除订单
   * */
  public void orderDelete(String orderId) {
    orderMapper.deleteByPrimaryKey(orderId);
  }

  /**
   * 查询订单
   * */
  public Order getOrderInfo(String orderId) {
    return orderMapper.selectByPrimaryKey(orderId);
  }

  /**
   * 修改订单
   * */
  public void updateOrderInfo(Order order) {
    orderMapper.updateUserIdByOrder(order);
  }

}
