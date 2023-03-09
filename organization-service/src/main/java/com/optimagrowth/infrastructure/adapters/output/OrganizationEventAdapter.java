package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.OrganizationEventChange;
import com.optimagrowth.domain.ActionEnum;
import com.optimagrowth.infrastructure.events.model.OrganizationChangeModel;
import com.optimagrowth.infrastructure.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrganizationEventAdapter implements OrganizationEventChange {
  private final Source source;

  public OrganizationEventAdapter(Source source) {
    this.source = source;
  }

  @Override
  public void publishOrganizationChange(ActionEnum action, String orgId) {

    OrganizationChangeModel change =  new OrganizationChangeModel(
            OrganizationChangeModel.class.getTypeName(),
            action.toString(),
            orgId,
            UserContext.getCorrelationId());

    log.info("Sending Kafka message {} for Organization Id: {}", action, orgId);
    //    Sends the message from a channel defined in the Source class
    source.output().send(MessageBuilder.withPayload(change).build());

  }
}
