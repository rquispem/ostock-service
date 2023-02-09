package com.optimagrowth.infrastructure.ports.input;

import com.optimagrowth.domain.License;
import java.util.Locale;

public interface LicenseServicePort {
  License getLicense(String licenseId, String organizationId);

  String createLicense(License license, String organizationId, Locale locale);

  String updateLicense(License license, String organizationId, Locale locale);

  String deleteLicense(String licenseId, String organizationId);
}
