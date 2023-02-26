package com.optimagrowth.infrastructure.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResolverConfig {

  @Bean
  public KeycloakConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
