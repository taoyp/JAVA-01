package com.lxtyp.basic.order.service;

import com.google.inject.internal.util.Lists;
import com.lxtyp.basic.generator.dao.OrderMapper;
import com.lxtyp.basic.generator.entity.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Order getOrder(String id) {
    return orderMapper.selectByInfo();
  }
//
//  /**
//   * 数据库url增加 &rewriteBatchedStatements=true，入表速率显著提升几十倍
//   * 表字段只有两个时，达到十秒内
//   * 测试十个字段，未达到
//   * 其他情况测试不完全，没达到十秒以内
//   * **/
//  public void jdbcSimple() {
//    BigDecimal bigDecimal = new BigDecimal(10);
//    Date date = new Date();
//    String sql = "insert into t_user (ID, NAME) values (?, ?)";
//    List<Object[]> list = Lists.newArrayList();
//    int inCnt = 1000000;
//
//    long start = System.currentTimeMillis();
//    for (int i=0; i<inCnt; i++) {
//      Object[] objects = new Object[]{i, "lxtyp"};
//      list.add(objects);
//    }
//
//    StopWatch stopWatch = new StopWatch();
//    stopWatch.start();
//    int[] results = jdbcTemplate.batchUpdate(sql, list);
//    stopWatch.stop();
//    long end = System.currentTimeMillis();
//    System.out.println("times = " + (end - start));
//    System.out.println("results.length = " + results.length);
//  }
//
//  public void orderIn() {
//    BigDecimal bigDecimal = new BigDecimal(10);
//    long start1 = System.currentTimeMillis();
//    long end = System.currentTimeMillis();
//    Date date = new Date();
//    int inCnt = 10000;
//    for (int j=0; j<10; j++) {
//      long start = System.currentTimeMillis();
//      Order order;
//      List<Order> list = new ArrayList<>();
//      for (int i=inCnt * j; i<inCnt * (j+1); i++) {
//        order = new Order();
//        order.setORDER_ID(i + "");
//        order.setORDER_ADDRESS("in Xian Now");
//        order.setORDER_PRICE(bigDecimal);
//        order.setORDER_PAY(bigDecimal);
//        order.setORDER_DISCOUNT(bigDecimal);
//        order.setSTATUS("0");
//        order.setUSER_ID("lxtyp");
//        order.setPAY_TYPE("0");
//        order.setCREATE_TIME(date);
//        order.setUPDATE_TIME(date);
//
//        list.add(order);
//        if (i % inCnt == 0) {
//          orderMapper.insert(list);
//          list = new ArrayList<>();
//        }
//      }
//      orderMapper.insert(list);
//      end = System.currentTimeMillis();
//      System.out.println("During::" + (end - start));
//    }
//    System.out.println("During:end:" + (end - start1));
//  }
//
//  public void jdbcTemplateIn() {
//    BigDecimal bigDecimal = new BigDecimal(10);
//    Date date = new Date();
//    String sql = "insert into TF_B_ORDER (ORDER_ID, USER_ID, STATUS, ORDER_PRICE, PAY_TYPE, ORDER_ADDRESS, ORDER_DISCOUNT, ORDER_PAY, CREATE_TIME, UPDATE_TIME) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    List<Object[]> list = Lists.newArrayList();
//    int inCnt = 1000000;
//
//    long start = System.currentTimeMillis();
//    for (int i=0; i<inCnt; i++) {
//      Object[] objects = new Object[]{i, "lxtyp", "0", bigDecimal, "0", "In Xian", bigDecimal, bigDecimal, date, date};
//      list.add(objects);
//    }
//
//    StopWatch stopWatch = new StopWatch();
//    stopWatch.start();
//    int[] results = jdbcTemplate.batchUpdate(sql, list);
//    stopWatch.stop();
//    long end = System.currentTimeMillis();
//    System.out.println("times = " + (end - start));
//    System.out.println("results.length = " + results.length);
//  }

}
