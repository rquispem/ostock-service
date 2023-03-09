package com.optimagrowth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  private final RedisProperties redisProperties;

  public RedisConfig(RedisProperties redisProperties) {
    this.redisProperties = redisProperties;
  }

  /*
   * Sets up the database connection to the Redis server
   */
  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    String hostname = redisProperties.getRedisServer();
    int port = Integer.parseInt(redisProperties.getRedisPort());
    RedisStandaloneConfiguration redisStandaloneConfiguration =
            new RedisStandaloneConfiguration(hostname, port);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  /*
   * Creates a RedisTemplate to carry out actions for our Redis server
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
