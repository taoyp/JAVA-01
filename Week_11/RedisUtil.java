package io.kimmking.cache.config;

import java.util.Collections;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @ClassName RedisLockConfig
 * @Date 2021/4/3 18:20
 * @Description TODO
 * @Status ISFINISH
 */
@Configuration
public class RedisUtil {
  /**
   * 加锁
   */
  public static boolean tryLock(Jedis jedis, String id, String lockKey, long timeout){
    String lock = jedis.set(lockKey, id, "NX", "PX", timeout);
    if("OK".equals(lock)){
      return true;
    }
    return false;
  }

  /**
   * 解锁
   */
  public static void unLock(Jedis jedis, String lockKey, String id) {
    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(id));
  }

  /**
   * decrease
   */
  public static long decrCount(Jedis jedis) {
    return jedis.decr("redisCount");
  }

  /**
   * init Count
   */
  public static void initCount(Jedis jedis) {
    jedis.set("redisCount", "5");
  }

}
