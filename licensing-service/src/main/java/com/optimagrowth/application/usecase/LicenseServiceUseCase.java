package com.optimagrowth.application.usecase;

import com.optimagrowth.application.ports.input.LicenseServicePort;
import com.optimagrowth.application.ports.output.LicenseRepository;
import com.optimagrowth.domain.License;
import com.optimagrowth.domain.Organization;
import com.optimagrowth.infrastructure.OrganizationDiscoverClientFactory;
import java.util.UUID;

public class LicenseServiceUseCase implements LicenseServicePort {

  private final LicenseRepository licenseRepository;
  private final OrganizationDiscoverClientFactory organizationDiscoverClientFactory;

  public LicenseServiceUseCase(LicenseRepository licenseRepository,
                               OrganizationDiscoverClientFactory organizationDiscoverClientFactory) {
    this.licenseRepository = licenseRepository;
    this.organizationDiscoverClientFactory = organizationDiscoverClientFactory;
  }

  @Override
  public License getLicense(String licenseId, String organizationId) {
    return licenseRepository
            .findByOrganizationIdAndLicenseId(organizationId, licenseId);

  }

  @Override
  public License createLicense(License license) {
    license.setLicenseId(UUID.randomUUID().toString());
    licenseRepository.save(license);
    return license;
  }

  @Override
  public License updateLicense(License license) {
    licenseRepository.save(license);
    return license;
  }

  @Override
  public void deleteLicense(String licenseId) {
    License license = new License();
    license.setLicenseId(licenseId);
    licenseRepository.delete(license);
  }

  @Override
  public License getLicenseWithClientType(String organizationId,
                                          String licenseId,
                                          String clientType) {
    License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId,
            licenseId);
    if (null == license) {
      throw new IllegalArgumentException("Error getting license with client");
    }
    Organization organization = retrieveOrganizationInfo(organizationId,
            clientType);
    if (null != organization) {
      license.setOrganizationName(organization.getName());
      license.setContactName(organization.getContactName());
      license.setContactEmail(organization.getContactEmail());
      license.setContactPhone(organization.getContactPhone());
    }
    return license;
  }

  private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
    Organization organization = null;

    switch (clientType) {
      case "feign":
        System.out.println("I am using the feign client");
        organization = organizationDiscoverClientFactory.getFeignClient().getOrganization(organizationId);
        break;
      case "rest":
        System.out.println("I am using the rest client");
        organization = organizationDiscoverClientFactory.getRestClient().getOrganization(organizationId);
        break;
      case "discovery":
        System.out.println("I am using the discovery client");
        organization = organizationDiscoverClientFactory.getDiscoveryClient().getOrganization(organizationId);
        break;
      default:
        organization = organizationDiscoverClientFactory.getRestClient().getOrganization(organizationId);
        break;
    }
    return organization;
  }
}
