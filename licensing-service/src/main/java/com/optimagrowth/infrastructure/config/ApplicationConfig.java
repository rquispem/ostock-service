package com.optimagrowth.infrastructure.config;

import com.optimagrowth.application.ports.input.LicenseServicePort;
import com.optimagrowth.application.ports.output.LicenseRepository;
import com.optimagrowth.application.usecase.LicenseServiceUseCase;
import com.optimagrowth.infrastructure.OrganizationDiscoverClientFactory;
import com.optimagrowth.infrastructure.adapters.output.LicenseRepositoryAdapter;
import com.optimagrowth.infrastructure.database.repositories.DatabaseLicenseRepository;
import com.optimagrowth.infrastructure.interceptors.UserContextInterceptor;
import com.optimagrowth.infrastructure.mapper.LicenseMapper;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ApplicationConfig {

  @Bean
  public LicenseServicePort licenseServicePort(LicenseRepository licenseRepository,
                                               OrganizationDiscoverClientFactory factory) {
    return new LicenseServiceUseCase(licenseRepository, factory);
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

  @Bean
  public LicenseRepository licenseRepository(DatabaseLicenseRepository databaseLicenseRepository,
                                             LicenseMapper licenseMapper) {
    return new LicenseRepositoryAdapter(databaseLicenseRepository, licenseMapper);
  }

  /*
   * @LoadBalanced Gets a list of all the instances for the organization services
   * With this bean definition in place, any time we use the @Autowired annotation and
   * inject a RestTemplate into a class, we’ll use the RestTemplate created in
   * with the UserContextInterceptor attached to it.
   */
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate template = new RestTemplate();
    List interceptors = template.getInterceptors();
    var userContextInterceptor = new UserContextInterceptor();
    if (Objects.isNull(interceptors)) {
      template.setInterceptors(Collections.singletonList(userContextInterceptor));
    } else {
      interceptors.add(userContextInterceptor);
      template.setInterceptors(interceptors);
    }
    return new RestTemplate();
  }
}
