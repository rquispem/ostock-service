package com.optimagrowth.infrastructure.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "organizations")
public class Organization {
  @Id
  @Column(name = "organization_id", nullable = false)
  private String id;
  @Column(name = "name", nullable = false)
  private  String name;
  @Column(name = "contact_name", nullable = false)
  private  String contactName;
  @Column(name = "contact_email", nullable = false)
  private  String contactEmail;
  @Column(name = "contact_phone", nullable = false)
  private  String contactPhone;
}
