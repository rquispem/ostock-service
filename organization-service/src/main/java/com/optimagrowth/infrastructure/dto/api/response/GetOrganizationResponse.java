package com.optimagrowth.infrastructure.dto.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetOrganizationResponse {
  private String id;
  private  String name;
  private  String contactName;
  private  String contactEmail;
  private  String contactPhone;
}
