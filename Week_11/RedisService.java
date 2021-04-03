package io.kimmking.cache.service;

import io.kimmking.cache.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName RedisService
 * @Date 2021/4/3 18:56
 * @Description TODO
 * @Status ISFINISH
 */
@Service
public class RedisService {
  private static final long expireTime = 30000L;

  @Autowired
  JedisPool jedisPool;

  public void tryDecreaseCount() {
    String redisId = "Redis_" + System.currentTimeMillis();
    Jedis jedis = jedisPool.getResource();
    String lockKey = "redisLock";
    boolean isContinue = false;
    while(!isContinue) {
      isContinue = RedisUtil.tryLock(jedis, redisId, lockKey, expireTime);
    }

    System.out.println("begin to decrease");
    long result = RedisUtil.decrCount(jedis);
    System.out.println("result=" + result);
    try {
      int sleepCnt = (int)(Math.random() * 3000);
      Thread.sleep(sleepCnt);
    } catch (Exception e) {
      e.printStackTrace();
    }

    RedisUtil.unLock(jedis, lockKey, redisId);
  }
}
