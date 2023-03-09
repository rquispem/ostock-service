package com.optimagrowth.infrastructure.config;

import com.optimagrowth.application.ports.input.OrganizationServicePort;
import com.optimagrowth.application.ports.output.OrganizationEventChange;
import com.optimagrowth.application.ports.output.OrganizationRepository;
import com.optimagrowth.application.usecase.OrganizationServiceUseCase;
import com.optimagrowth.infrastructure.adapters.output.OrganizationRepositoryAdapter;
import com.optimagrowth.infrastructure.database.repositories.DatabaseOrganizationRepository;
import com.optimagrowth.infrastructure.mapper.OrganizationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public OrganizationServicePort organizationServicePort(OrganizationRepository organizationRepository,
                                                         OrganizationEventChange organizationEventChange) {
    return new OrganizationServiceUseCase(organizationRepository, organizationEventChange);
  }

  @Bean
  public OrganizationRepository organizationRepository(DatabaseOrganizationRepository repository,
                                                       OrganizationMapper organizationMapper) {
    return new OrganizationRepositoryAdapter(repository, organizationMapper);
  }
}
