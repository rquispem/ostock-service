package com.optimagrowth.infrastructure.dto.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
public class GetLicenseResponse extends RepresentationModel<GetLicenseResponse> {
  private int id;
  private String licenseId;
  private String description;
  private String organizationId;
  private String productName;
  private String licenseType;
}
