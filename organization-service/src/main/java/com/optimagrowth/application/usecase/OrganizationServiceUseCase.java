package com.optimagrowth.application.usecase;

import com.optimagrowth.application.ports.input.OrganizationServicePort;
import com.optimagrowth.application.ports.output.OrganizationEventChange;
import com.optimagrowth.application.ports.output.OrganizationRepository;
import com.optimagrowth.domain.ActionEnum;
import com.optimagrowth.domain.Organization;
import java.util.Optional;
import java.util.UUID;

public class OrganizationServiceUseCase implements OrganizationServicePort {

  private final OrganizationRepository organizationRepository;
  private final OrganizationEventChange organizationEventChange;

  public OrganizationServiceUseCase(OrganizationRepository organizationRepository,
                                    OrganizationEventChange organizationEventChange) {
    this.organizationRepository = organizationRepository;
    this.organizationEventChange = organizationEventChange;
  }

  @Override
  public Organization findById(String organizationId) {
    Optional<Organization> opt = organizationRepository.findById(organizationId);
    organizationEventChange.publishOrganizationChange(ActionEnum.GET, organizationId);
    return (opt.isPresent()) ? opt.get() : null;
  }

  @Override
  public Organization create(Organization organization) {
    organization.setId(UUID.randomUUID().toString());
    var organizationSaved = organizationRepository.save(organization);
    organizationEventChange.publishOrganizationChange(ActionEnum.CREATED, organization.getId());
    return organizationSaved;
  }

  @Override
  public void update(Organization organization) {
    organizationRepository.save(organization);
    organizationEventChange.publishOrganizationChange(ActionEnum.UPDATED, organization.getId());
  }

  @Override
  public void delete(String organizationId) {
    organizationRepository.deleteById(organizationId);
    organizationEventChange.publishOrganizationChange(ActionEnum.DELETED, organizationId);
  }
}
