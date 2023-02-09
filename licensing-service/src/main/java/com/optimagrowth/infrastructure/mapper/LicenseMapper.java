package com.optimagrowth.infrastructure.mapper;

import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.dto.CreateLicenseRequest;
import com.optimagrowth.infrastructure.dto.GetLicenseResponse;
import com.optimagrowth.infrastructure.dto.UpdateLicenseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LicenseMapper {

  GetLicenseResponse ToGetLicenseResponse(License license);

  License toLicenseFrom(UpdateLicenseRequest request);

  License toLicenseFrom(CreateLicenseRequest request);

  CreateLicenseRequest toCreateLicenseRequest(License license);

  UpdateLicenseRequest toUpdateLicenseRequest(License license);
}
