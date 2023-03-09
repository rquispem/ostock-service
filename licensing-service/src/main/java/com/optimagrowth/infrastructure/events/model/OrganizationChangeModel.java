package com.optimagrowth.infrastructure.events.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrganizationChangeModel {
  private String type;
  private String action;
  private String organizationId;
  private String correlationId;

  public OrganizationChangeModel(String type,
                                 String action, String organizationId,
                                 String correlationId) {
    this.type = type;
    this.action = action;
    this.organizationId = organizationId;
    this.correlationId = correlationId;
  }
}
