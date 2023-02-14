package com.optimagrowth.application.ports.output;

import com.optimagrowth.domain.Organization;

public interface OrganizationDiscoveryClient {
  Organization getOrganization(String organizationId);
}
