package com.optimagrowth.application.ports.output;

import com.optimagrowth.domain.License;
import java.util.List;

public interface LicenseRepository {
  List<License> findByOrganizationId(String organizationId);

  License findByOrganizationIdAndLicenseId(String organizationId,
                                           String licenseId);

  void save(License license);

  void delete(License license);
}
