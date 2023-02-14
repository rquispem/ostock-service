package com.optimagrowth.infrastructure;

import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.infrastructure.adapters.output.OrganizationDiscoveryClientAdapter;
import com.optimagrowth.infrastructure.adapters.output.OrganizationFeignClientAdapter;
import com.optimagrowth.infrastructure.adapters.output.OrganizationLoadBalancedRestTemplateClientAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDiscoverClientFactory {
  private final OrganizationDiscoveryClientAdapter organizationDiscoveryClientAdapter;
  private final OrganizationLoadBalancedRestTemplateClientAdapter restTemplateClientAdapter;
  private final OrganizationFeignClientAdapter organizationFeignClientAdapter;

  public OrganizationDiscoverClientFactory(OrganizationDiscoveryClientAdapter organizationDiscoveryClientAdapter,
                                           OrganizationLoadBalancedRestTemplateClientAdapter restTemplateClientAdapter,
                                           OrganizationFeignClientAdapter organizationFeignClientAdapter) {
    this.organizationDiscoveryClientAdapter = organizationDiscoveryClientAdapter;
    this.restTemplateClientAdapter = restTemplateClientAdapter;
    this.organizationFeignClientAdapter = organizationFeignClientAdapter;
  }

  public OrganizationDiscoveryClient getFeignClient() {
    return organizationFeignClientAdapter;
  }

  public OrganizationDiscoveryClient getRestClient() {
    return restTemplateClientAdapter;
  }

  public OrganizationDiscoveryClient getDiscoveryClient() {
    return organizationDiscoveryClientAdapter;
  }
}
