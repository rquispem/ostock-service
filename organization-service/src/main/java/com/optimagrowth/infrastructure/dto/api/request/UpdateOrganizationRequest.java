package com.optimagrowth.infrastructure.dto.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateOrganizationRequest {
  private String id;
  private  String name;
  private  String contactName;
  private  String contactEmail;
  private  String contactPhone;
}
