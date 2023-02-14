package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationRepository;
import com.optimagrowth.domain.Organization;
import com.optimagrowth.infrastructure.database.repositories.DatabaseOrganizationRepository;
import com.optimagrowth.infrastructure.mapper.OrganizationMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRepositoryAdapter implements OrganizationRepository {
  private final DatabaseOrganizationRepository databaseOrganizationRepository;
  private final OrganizationMapper organizationMapper;

  public OrganizationRepositoryAdapter(DatabaseOrganizationRepository databaseOrganizationRepository,
                                       OrganizationMapper organizationMapper) {
    this.databaseOrganizationRepository = databaseOrganizationRepository;
    this.organizationMapper = organizationMapper;
  }

  @Override
  public Optional<Organization> findById(String organizationId) {
    return databaseOrganizationRepository.findById(organizationId)
            .map(organizationMapper::toModelFromEntity);
  }

  @Override
  public Organization save(Organization organization) {
    com.optimagrowth.infrastructure.database.entity.Organization organizationEntity =
            organizationMapper.toEntityFromModel(organization);
    return organizationMapper.toModelFromEntity(databaseOrganizationRepository.save(organizationEntity));
  }

  @Override
  public void deleteById(String organizationId) {
    databaseOrganizationRepository.deleteById(organizationId);
  }
}
