package com.optimagrowth.application;

import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.ports.input.LicenseServicePort;
import java.util.Locale;
import java.util.Random;
import org.springframework.context.MessageSource;

public class LicenseServiceUseCase implements LicenseServicePort {

  private final MessageSource messageSource;

  public LicenseServiceUseCase(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public License getLicense(String licenseId, String organizationId) {
    License license = new License();
    license.setId(new Random().nextInt(1000));
    license.setLicenseId(licenseId);
    license.setOrganizationId(organizationId);
    license.setDescription("Software product");
    license.setProductName("Ostock");
    license.setLicenseType("full");
    return license;
  }

  @Override
  public String createLicense(License license,
                              String organizationId,
                              Locale locale) {
    String responseMessage = null;
    if (license != null) {
      license.setOrganizationId(organizationId);
      responseMessage = String.format(messageSource.getMessage("license.create.message", null, locale),
              license);
    }
    return responseMessage;
  }

  @Override
  public String updateLicense(License license,
                              String organizationId,
                              Locale locale) {
    String responseMessage = null;
    if (license != null) {
      license.setOrganizationId(organizationId);
      responseMessage = String.format(messageSource.getMessage("license.update.message", null, locale),
              license);
    }
    return responseMessage;
  }

  @Override
  public String deleteLicense(String licenseId, String organizationId) {
    String responseMessage = null;
    responseMessage = String.format("Deleting license with id %s for the organization %s",
            licenseId, organizationId);
    return responseMessage;
  }
}
