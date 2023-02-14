package com.optimagrowth.application.ports.input;

import com.optimagrowth.domain.Organization;

public interface OrganizationServicePort {

  Organization findById(String organizationId);

  Organization create(Organization organization);

  void update(Organization organization);

  void delete(String organizationId);
}
