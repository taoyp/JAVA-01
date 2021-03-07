package com.lxtyp.basic.order.controller;

import com.lxtyp.basic.annotation.ReadOnly;
import com.lxtyp.basic.generator.entity.Order;
import com.lxtyp.basic.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ReadOnlyController
 * @Date 2021/3/7 07:34
 * @Description TODO
 * @Status ISFINISH
 */
@RestController
@RequestMapping("/read")
public class ReadOnlyController {
  @Autowired
  private OrderService orderService;

  /**
   * 只读，动态切换从库
   * */
  @RequestMapping("/order")
  @ReadOnly
  public Order getOrder(String id) {
    return orderService.getOrder(id);
  }

  /**
   * 读主库
   * */
  @RequestMapping("/orderW")
  public Order getOrderW(String id) {
    return orderService.getOrder(id);
  }
}
