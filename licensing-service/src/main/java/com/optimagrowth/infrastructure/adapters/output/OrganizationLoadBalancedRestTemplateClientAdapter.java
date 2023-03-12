package com.optimagrowth.infrastructure.adapters.output;

import brave.Tracer;
import com.optimagrowth.application.ports.output.OrganizationDiscoveryClient;
import com.optimagrowth.domain.Organization;
import com.optimagrowth.infrastructure.database.repositories.OrganizationRedisRepository;
import com.optimagrowth.infrastructure.mapper.OrganizationMapper;
import com.optimagrowth.infrastructure.utils.UserContext;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrganizationLoadBalancedRestTemplateClientAdapter implements OrganizationDiscoveryClient {

  //  private final RestTemplate restTemplate;
  /*  KeycloakRestTemplate is a drop-in replacement for the standard RestTemplate.
     It handles the propagation of the access token.
  */
  private final KeycloakRestTemplate restTemplate;
  private final String serviceUri;
  private final OrganizationRedisRepository organizationRedisRepository;
  private final OrganizationMapper organizationMapper;
  private final Tracer tracer;

  public OrganizationLoadBalancedRestTemplateClientAdapter(KeycloakRestTemplate restTemplate,
                                                           @Value("${ostock.gateway.service.uri}") String serviceUri,
                                                           OrganizationRedisRepository organizationRedisRepository,
                                                           OrganizationMapper organizationMapper,
                                                           Tracer tracer) {
    this.restTemplate = restTemplate;
    this.serviceUri = serviceUri;
    this.organizationRedisRepository = organizationRedisRepository;
    this.organizationMapper = organizationMapper;
    this.tracer = tracer;
  }

  /*
   * When using a Load Balancer– backed RestTemplate, builds the target URL with the Eureka service ID
   */
  @CircuitBreaker(name = "restOrganizationService")
  @Override
  public Organization getOrganization(String organizationId) {
    log.debug("In Licensing Service.getOrganization: {}",
            UserContext.getCorrelationId());

    var organization = checkRedisCache(organizationId);

    if (organization != null) {
      log.info("I have successfully retrieved an organization {} from the redis cache: {}",
              organizationId, organization);
      return organizationMapper.toModelFrom(organization);
    }
    log.info("Unable to locate organiation from the redis cache: {}", organizationId);
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
    var organizationFromService = restExchange.getBody();
    if (Objects.nonNull(organizationFromService)) {
      cacheOrganizationObject(organizationMapper.toEntityFrom(organizationFromService));
    }
    return restExchange.getBody();
  }

  private com.optimagrowth.infrastructure.database.entity.Organization checkRedisCache(String organizationId) {
    try {
      return organizationRedisRepository.findById(organizationId).orElse(null);
    } catch (Exception ex) {
      log.error("Error encountered while trying to retrieve organization {} "
              + "check Redis cache. Exception {}", organizationId, ex);
      return null;
    }
  }

  private void cacheOrganizationObject(com.optimagrowth.infrastructure.database.entity.Organization organization) {
    try {
      organizationRedisRepository.save(organization);
    } catch (Exception ex) {
      log.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
    }
  }
}
