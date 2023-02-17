package com.optimagrowth.infrastructure.utils;

import java.util.Objects;
import org.springframework.util.Assert;

public class UserContextHolder {
  private static final ThreadLocal<UserContext> userContext =
          new ThreadLocal<>();

  public static final UserContext getContext() {
    var context = userContext.get();
    if (Objects.isNull(context)) {
      context = createEmptyContext();
      userContext.set(context);
    }
    return context;
  }

  public static final void setContext(UserContext context) {
    Assert.notNull(context, "Only non-null UserContext instances are permitted");
    userContext.set(context);
  }

  private static UserContext createEmptyContext() {
    return new UserContext();
  }
}
