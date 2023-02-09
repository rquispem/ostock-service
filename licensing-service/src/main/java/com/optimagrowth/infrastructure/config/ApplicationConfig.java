package com.optimagrowth.infrastructure.config;

import com.optimagrowth.application.LicenseServiceUseCase;
import com.optimagrowth.infrastructure.ports.input.LicenseServicePort;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ApplicationConfig {

  @Bean
  public LicenseServicePort licenseServicePort(MessageSource messageSource) {
    return new LicenseServiceUseCase(messageSource);
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource =
            new ResourceBundleMessageSource();
    //Doesn’t throw an error if a message isn’t found, instead it returns the message code
    messageSource.setUseCodeAsDefaultMessage(true);
    //Sets the base name of the languages properties files
    messageSource.setBasenames("messages");
    return messageSource;
  }
}
