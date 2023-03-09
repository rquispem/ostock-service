package com.optimagrowth.infrastructure.database.repositories;

import com.optimagrowth.infrastructure.database.entity.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRedisRepository extends CrudRepository<Organization, String> {
}
