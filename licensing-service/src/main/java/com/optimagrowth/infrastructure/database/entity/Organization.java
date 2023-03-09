package com.optimagrowth.infrastructure.database.entity;

import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@ToString
// Sets the name of the hash in the Redis server where the organization data is stored
@RedisHash("organization")
public class Organization {
  @Id
  private String id;
  private  String name;
  private  String contactName;
  private  String contactEmail;
  private  String contactPhone;
}
