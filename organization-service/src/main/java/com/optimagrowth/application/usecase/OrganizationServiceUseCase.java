package com.optimagrowth.application.usecase;

import com.optimagrowth.application.ports.input.OrganizationServicePort;
import com.optimagrowth.application.ports.output.OrganizationRepository;
import com.optimagrowth.domain.Organization;
import java.util.Optional;
import java.util.UUID;

public class OrganizationServiceUseCase implements OrganizationServicePort {

  private final OrganizationRepository organizationRepository;

  public OrganizationServiceUseCase(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  @Override
  public Organization findById(String organizationId) {
    Optional<Organization> opt = organizationRepository.findById(organizationId);
    return (opt.isPresent()) ? opt.get() : null;
  }

  @Override
  public Organization create(Organization organization) {
    organization.setId(UUID.randomUUID().toString());
    return organizationRepository.save(organization);
  }

  @Override
  public void update(Organization organization) {
    organizationRepository.save(organization);
  }

  @Override
  public void delete(String organizationId) {
    organizationRepository.deleteById(organizationId);
  }
}
