package com.optimagrowth.infrastructure.database.repositories;

import com.optimagrowth.infrastructure.database.entity.Organization;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DatabaseOrganizationRepository extends CrudRepository<Organization, String> {
  Optional<Organization> findById(String organizationId);
}
