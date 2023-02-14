package com.optimagrowth.application.ports.output;

import com.optimagrowth.domain.Organization;
import java.util.Optional;

public interface OrganizationRepository {
  Optional<Organization> findById(String organizationId);

  Organization save(Organization organization);

  void deleteById(String organizationId);
}
