package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.domain.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Identifies your service to Feign
@FeignClient("organization-service")
public interface OrganizationFeignClientAdapter extends OrganizationDiscoveryClient {
  @GetMapping(
          value = "/v1/organization/{organizationId}",
          consumes = "application/json")
  @Override
  Organization getOrganization(@PathVariable("organizationId")String organizationId);
}
