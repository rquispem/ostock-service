package com.optimagrowth.infrastructure.interceptors;

import com.optimagrowth.infrastructure.utils.UserContext;
import com.optimagrowth.infrastructure.utils.UserContextHolder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

  /*
   * Invokes intercept() before the actual HTTP service call occurs by the RestTemplate
   * To use UserContextInterceptor, we need to define a RestTemplate bean and
   *  then add UserContextInterceptor to it
   */
  @Override
  public ClientHttpResponse intercept(HttpRequest request,
                                      byte[] body,
                                      ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders headers = request.getHeaders();
    headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
    headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
    log.info("In UserContextInterceptor");
    return execution.execute(request, body);
  }
}
