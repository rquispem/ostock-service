package com.optimagrowth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class LicenseServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(LicenseServiceApplication.class, args);
  }

  //@StreamListexner(Sink.INPUT)
  //  public void loggerSink(OrganizationChangeModel orgChange) {
  //    log.info("Received an {} event for organization id {}",
  //            orgChange.getAction(), orgChange.getOrganizationId());
  //
  //  }
}
