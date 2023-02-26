package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.domain.Organization;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationLoadBalancedRestTemplateClientAdapter implements OrganizationDiscoveryClient {

  //  private final RestTemplate restTemplate;
  /*  KeycloakRestTemplate is a drop-in replacement for the standard RestTemplate.
     It handles the propagation of the access token.
  */
  private final KeycloakRestTemplate restTemplate;
  private final String serviceUri;

  public OrganizationLoadBalancedRestTemplateClientAdapter(KeycloakRestTemplate restTemplate,
                                                           @Value("${ostock.gateway.service.uri}") String serviceUri) {
    this.restTemplate = restTemplate;
    this.serviceUri = serviceUri;
  }

  /*
   * When using a Load Balancer– backed RestTemplate, builds the target URL with the Eureka service ID
   */
  @CircuitBreaker(name = "restOrganizationService")
  @Override
  public Organization getOrganization(String organizationId) {
    String url = String.format("%s/organization/v1/organization/%s", serviceUri, organizationId);
    ResponseEntity<Organization> restExchange =
            restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null, Organization.class, organizationId);
    //            restTemplate.exchange(
    //                    "http://organization-service/v1/organization/{organizationId}",
    //                    HttpMethod.GET, null,
    //                    Organization.class, organizationId);
    return restExchange.getBody();
  }
}
