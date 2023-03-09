package com.optimagrowth.application.ports.output;

import com.optimagrowth.domain.ActionEnum;

public interface OrganizationEventChange {
  void publishOrganizationChange(ActionEnum action, String orgId);
}
