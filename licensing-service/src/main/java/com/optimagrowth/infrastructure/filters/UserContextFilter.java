package com.optimagrowth.infrastructure.filters;

import com.optimagrowth.infrastructure.utils.UserContext;
import com.optimagrowth.infrastructure.utils.UserContextHolder;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserContextFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("on init");
  }

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    UserContextHolder.getContext().setCorrelationId(
            httpServletRequest.getHeader(
                    UserContext.CORRELATION_ID));
    UserContextHolder.getContext().setUserId(
            httpServletRequest.getHeader(
                    UserContext.USER_ID));
    UserContextHolder.getContext().setAuthToken(
            httpServletRequest.getHeader(
                    UserContext.AUTH_TOKEN));
    UserContextHolder.getContext().setOrganizationId(
            httpServletRequest.getHeader(
                    UserContext.ORGANIZATION_ID));

    log.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
    filterChain.doFilter(httpServletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    log.info("on destroy");
  }
}
