package com.optimagrowth.infrastructure.mapper;

import com.optimagrowth.infrastructure.database.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

  com.optimagrowth.domain.Organization toModelFrom(Organization organization);

  Organization toEntityFrom(com.optimagrowth.domain.Organization organization);
}
