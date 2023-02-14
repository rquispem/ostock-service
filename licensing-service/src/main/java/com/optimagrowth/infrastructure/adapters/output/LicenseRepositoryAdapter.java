package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.LicenseRepository;
import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.database.repositories.DatabaseLicenseRepository;
import com.optimagrowth.infrastructure.mapper.LicenseMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LicenseRepositoryAdapter implements LicenseRepository {
  private final DatabaseLicenseRepository licenseRepository;
  private final LicenseMapper mapper;

  public LicenseRepositoryAdapter(DatabaseLicenseRepository licenseRepository,
                                  LicenseMapper mapper) {
    this.licenseRepository = licenseRepository;
    this.mapper = mapper;
  }

  @Override
  public List<License> findByOrganizationId(String organizationId) {
    var licenses = licenseRepository.findByOrganizationId(organizationId);
    return licenses.stream().map(mapper::toModelFromEntity)
            .collect(Collectors.toList());
  }

  @Override
  public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId) {
    var license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
    return mapper.toModelFromEntity(license);
  }

  @Override
  public void save(License license) {
    licenseRepository.save(mapper.toEntityFromModel(license));
  }

  @Override
  public void delete(License license) {
    licenseRepository.delete(mapper.toEntityFromModel(license));
  }
}
