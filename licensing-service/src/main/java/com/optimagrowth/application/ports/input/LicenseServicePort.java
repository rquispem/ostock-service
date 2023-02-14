package com.optimagrowth.application.ports.input;

import com.optimagrowth.domain.License;

public interface LicenseServicePort {
  License getLicense(String licenseId, String organizationId);

  License createLicense(License license);

  License updateLicense(License license);

  void deleteLicense(String licenseId);

  License getLicenseWithClientType(String organizationId, String licenseId, String clientType);
}
