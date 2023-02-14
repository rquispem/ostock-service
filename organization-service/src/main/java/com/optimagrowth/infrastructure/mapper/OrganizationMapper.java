package com.optimagrowth.infrastructure.mapper;

import com.optimagrowth.domain.Organization;
import com.optimagrowth.infrastructure.dto.api.request.CreateOrganizationRequest;
import com.optimagrowth.infrastructure.dto.api.request.UpdateOrganizationRequest;
import com.optimagrowth.infrastructure.dto.api.response.CreateOrganizationResponse;
import com.optimagrowth.infrastructure.dto.api.response.GetOrganizationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {
  Organization toModelFromEntity(com.optimagrowth.infrastructure.database.entity.Organization organization);

  com.optimagrowth.infrastructure.database.entity.Organization toEntityFromModel(Organization organization);

  GetOrganizationResponse ToGetOrganizationResponse(Organization organization);

  Organization toModelFrom(UpdateOrganizationRequest organization);

  Organization toModelFrom(CreateOrganizationRequest organization);

  CreateOrganizationResponse toCreateOrganizationResponseFrom(Organization organization);
}
