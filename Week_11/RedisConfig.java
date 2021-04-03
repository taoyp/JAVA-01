package io.kimmking.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName RedisConfig
 * @Date 2021/4/3 18:24
 * @Description TODO
 * @Status ISFINISH
 */
@Configuration
public class RedisConfig {
  @Bean
  public JedisPool genJedisPool() {
    JedisPool jedisPool = new JedisPool("localhost", 6379);
    RedisUtil.initCount(jedisPool.getResource());
    return jedisPool;
  }
}
