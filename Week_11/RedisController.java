package io.kimmking.cache.controller;

import io.kimmking.cache.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisController
 * @Date 2021/4/3 19:58
 * @Description TODO
 * @Status ISFINISH
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
  @Autowired
  RedisService redisService;

  @RequestMapping(value = "/decr")
  public String decreaseCount() throws Exception {
    redisService.tryDecreaseCount();
    Thread.sleep(3000);
    return "OK";
  }
}
