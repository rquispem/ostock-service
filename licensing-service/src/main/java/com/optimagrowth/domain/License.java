package com.optimagrowth.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class License {
  private int id;
  private String licenseId;
  private String description;
  private String organizationId;
  private String productName;
  private String licenseType;
  private String comment;
  private String organizationName;
  private String contactName;
  private String contactPhone;
  private String contactEmail;

  public License withComment(String comment) {
    this.setComment(comment);
    return this;
  }

}
