package com.optimagrowth.infrastructure.adapters.input;

import com.optimagrowth.application.ports.input.OrganizationServicePort;
import com.optimagrowth.infrastructure.dto.api.request.CreateOrganizationRequest;
import com.optimagrowth.infrastructure.dto.api.request.UpdateOrganizationRequest;
import com.optimagrowth.infrastructure.dto.api.response.CreateOrganizationResponse;
import com.optimagrowth.infrastructure.dto.api.response.GetOrganizationResponse;
import com.optimagrowth.infrastructure.mapper.OrganizationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {
  private final OrganizationServicePort organizationService;
  private final OrganizationMapper organizationMapper;

  public OrganizationController(OrganizationServicePort organizationService,
                                OrganizationMapper organizationMapper) {
    this.organizationService = organizationService;
    this.organizationMapper = organizationMapper;
  }

  @GetMapping(value = "/{organizationId}")
  public ResponseEntity<GetOrganizationResponse> getOrganization(
          @PathVariable("organizationId") String organizationId) {
    var organization = organizationService.findById(organizationId);
    return ResponseEntity.ok(organizationMapper.ToGetOrganizationResponse(organization));
  }

  @PutMapping(value = "/{organizationId}")
  public void updateOrganization(@PathVariable("organizationId") String id,
                                  @RequestBody UpdateOrganizationRequest organization) {
    organizationService.update(organizationMapper.toModelFrom(organization));
  }

  @PostMapping
  public ResponseEntity<CreateOrganizationResponse>  saveOrganization(
          @RequestBody CreateOrganizationRequest organization) {
    return ResponseEntity.ok(organizationMapper
            .toCreateOrganizationResponseFrom(organizationService.create(organizationMapper
                    .toModelFrom(organization))));
  }

  @DeleteMapping(value = "/{organizationId}")
  public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
    organizationService.delete(organizationId);
  }

}
