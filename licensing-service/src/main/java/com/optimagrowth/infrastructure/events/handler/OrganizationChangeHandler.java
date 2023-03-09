package com.optimagrowth.infrastructure.events.handler;

import com.optimagrowth.infrastructure.events.CustomChannels;
import com.optimagrowth.infrastructure.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannels.class)
@Slf4j
public class OrganizationChangeHandler {

  @StreamListener("inboundOrgChanges")
  public void loggerSink(OrganizationChangeModel organization) {

    log.debug("Received a message of type " + organization.getType());

    switch (organization.getAction()) {
      case "GET":
        log.debug("Received a GET event from the organization service for organization id {}",
                organization.getOrganizationId());
        break;
      case "CREATED":
        log.debug("Received a SAVE event from the organization service for organization id {}",
                organization.getOrganizationId());
        break;
      case "UPDATED":
        log.debug("Received a UPDATE event from the organization service for organization id {}",
                organization.getOrganizationId());
        break;
      case "DELETED":
        log.debug("Received a DELETE event from the organization service for organization id {}",
                organization.getOrganizationId());
        break;
      default:
        log.error("Received an UNKNOWN event from the organization service of type {}", organization.getType());
        break;
    }
  }
}
