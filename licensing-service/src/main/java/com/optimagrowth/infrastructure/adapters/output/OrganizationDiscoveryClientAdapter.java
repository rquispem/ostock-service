package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.domain.Organization;
import java.util.List;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationDiscoveryClientAdapter implements OrganizationDiscoveryClient {
  //You use this class to interact with the Spring Cloud Load Balancer
  private final DiscoveryClient discoveryClient;

  public OrganizationDiscoveryClientAdapter(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @Override
  public Organization getOrganization(String organizationId) {
    RestTemplate restTemplate = new RestTemplate();
    //Gets a list of all the instances of the organization services
    final List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
    if (instances.size() == 0) {
      return null;
    }
    String serviceUri = String.format("%s/v1/organization/%s", instances.get(0).getUri().toString(), organizationId);

    ResponseEntity<Organization> restExchange =
            restTemplate.exchange(
                    serviceUri, HttpMethod.GET,
                    null, Organization.class, organizationId);

    return restExchange.getBody();
  }
}
