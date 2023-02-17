package com.optimagrowth.application.ports.input;

import com.optimagrowth.domain.License;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface LicenseServicePort {
  License getLicense(String licenseId, String organizationId) throws TimeoutException;

  License createLicense(License license);

  License updateLicense(License license);

  void deleteLicense(String licenseId);

  License getLicenseWithClientType(String organizationId, String licenseId, String clientType) throws TimeoutException;

  List<License> getLicensesByOrganization(String organizationId) throws TimeoutException;
}
