package com.optimagrowth.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RedisProperties {
  @Value("${redis.server}")
  private String redisServer;
  @Value("${redis.port}")
  private String redisPort;
}
