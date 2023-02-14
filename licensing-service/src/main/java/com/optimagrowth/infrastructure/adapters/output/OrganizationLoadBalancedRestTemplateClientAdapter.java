package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.domain.Organization;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationLoadBalancedRestTemplateClientAdapter implements OrganizationDiscoveryClient {

  private final RestTemplate restTemplate;

  public OrganizationLoadBalancedRestTemplateClientAdapter(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /*
   * When using a Load Balancer– backed RestTemplate, builds the target URL with the Eureka service ID
   */
  @Override
  public Organization getOrganization(String organizationId) {
    ResponseEntity<Organization> restExchange =
            restTemplate.exchange(
                    "http://organization-service/v1/organization/{organizationId}",
                    HttpMethod.GET, null,
                    Organization.class, organizationId);
    return restExchange.getBody();
  }
}
