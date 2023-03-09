package com.optimagrowth.infrastructure.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {

  /*
   * Returns a SubscribableChannel class for each channel exposed by @Input
   */
  @Input("inboundOrgChanges") // Names the channel
  SubscribableChannel orgs();
}
