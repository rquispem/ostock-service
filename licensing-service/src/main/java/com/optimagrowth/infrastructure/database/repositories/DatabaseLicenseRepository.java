package com.optimagrowth.infrastructure.database.repositories;

import com.optimagrowth.infrastructure.database.entity.License;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DatabaseLicenseRepository extends CrudRepository<License, String> {

  List<License> findByOrganizationId(String organizationId);

  License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

}
