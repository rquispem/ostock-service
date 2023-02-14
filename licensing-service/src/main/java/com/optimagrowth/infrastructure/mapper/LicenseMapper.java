package com.optimagrowth.infrastructure.mapper;

import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.dto.api.request.CreateLicenseRequest;
import com.optimagrowth.infrastructure.dto.api.request.UpdateLicenseRequest;
import com.optimagrowth.infrastructure.dto.api.response.GetLicenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LicenseMapper {

  GetLicenseResponse ToGetLicenseResponse(License license);

  License toLicenseFrom(UpdateLicenseRequest request);

  License toLicenseFrom(CreateLicenseRequest request);

  CreateLicenseRequest toCreateLicenseRequest(License license);

  UpdateLicenseRequest toUpdateLicenseRequest(License license);

  License toModelFromEntity(com.optimagrowth.infrastructure.database.entity.License license);

  com.optimagrowth.infrastructure.database.entity.License toEntityFromModel(License license);
}
