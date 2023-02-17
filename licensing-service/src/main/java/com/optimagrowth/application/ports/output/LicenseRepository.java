package com.optimagrowth.application.ports.output;

import com.optimagrowth.domain.License;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface LicenseRepository {
  List<License> findByOrganizationId(String organizationId) throws TimeoutException;

  License findByOrganizationIdAndLicenseId(String organizationId,
                                           String licenseId) throws TimeoutException;

  void save(License license);

  void delete(License license);
}
