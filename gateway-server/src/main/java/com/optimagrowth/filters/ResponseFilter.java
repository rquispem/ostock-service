package com.optimagrowth.filters;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
public class ResponseFilter {

  private static final Logger logger =
          LoggerFactory.getLogger(ResponseFilter.class);

  private final FilterUtils filterUtils;
  //Sets the entry point to access trace and span ID information
  private final Tracer tracer;

  @Autowired
  public ResponseFilter(FilterUtils filterUtils, Tracer tracer) {
    this.filterUtils = filterUtils;
    this.tracer = tracer;
  }

  @Bean
  public GlobalFilter postGlobalFilter() {
    return (exchange, chain) ->  {
      final String traceId = Optional.ofNullable(tracer.currentSpan())
              .map(Span::context)
              .map(TraceContext::traceIdString)
              .orElse("null");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
          HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
          String correlationId =
                  filterUtils.
                          getCorrelationId(requestHeaders);
          logger.info("Adding the correlation id to the outbound headers. {}",
                  correlationId);
          exchange.getResponse().getHeaders().
                  add(FilterUtils.CORRELATION_ID,
                          correlationId);

//          String traceId = tracer.currentSpan().context().traceIdString();
          logger.info("Adding the traceId to the outbound headers. {}",
                  traceId);
          exchange.getResponse().getHeaders().
                  add(FilterUtils.TRACE_ID,
                          traceId);
          logger.info("Completing outgoing request for {}.", exchange.getRequest().getURI());
        }));
    };

  }
}
