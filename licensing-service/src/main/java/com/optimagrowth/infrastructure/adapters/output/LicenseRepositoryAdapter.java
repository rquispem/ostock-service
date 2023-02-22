package com.optimagrowth.infrastructure.adapters.output;

import com.optimagrowth.application.ports.output.LicenseRepository;
import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.database.repositories.DatabaseLicenseRepository;
import com.optimagrowth.infrastructure.mapper.LicenseMapper;
import com.optimagrowth.infrastructure.utils.UserContextHolder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LicenseRepositoryAdapter implements LicenseRepository {
  private final DatabaseLicenseRepository licenseRepository;
  private final LicenseMapper mapper;

  public LicenseRepositoryAdapter(DatabaseLicenseRepository licenseRepository,
                                  LicenseMapper mapper) {
    this.licenseRepository = licenseRepository;
    this.mapper = mapper;
  }

  @CircuitBreaker(name = "circuitLicenseRepositoryAdapter", fallbackMethod = "buildFallbackLicenseList")
  @RateLimiter(name = "rateLimiterLicenseService", fallbackMethod = "buildFallbackLicenseList")
  @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
  @Bulkhead(name = "bulkheadLicenseService", type = Bulkhead.Type.SEMAPHORE,
          fallbackMethod = "buildFallbackLicenseList")
  @Override
  public List<License> findByOrganizationId(String organizationId) throws TimeoutException {
    log.debug("findByOrganizationId Correlation id: {}",
            UserContextHolder.getContext().getCorrelationId());
    randomlyRunLong();
    var licenses = licenseRepository.findByOrganizationId(organizationId);
    return licenses.stream().map(mapper::toModelFromEntity)
            .collect(Collectors.toList());
  }

  @CircuitBreaker(name = "circuitLicenseRepositoryAdapter",
          fallbackMethod = "buildFallbackLicense")
  @RateLimiter(name = "rateLimiterLicenseService", fallbackMethod = "buildFallbackLicense")
  @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicense")
  @Bulkhead(name = "bulkheadLicenseService", type = Bulkhead.Type.SEMAPHORE,
          fallbackMethod = "buildFallbackLicense")
  @Override
  public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId) throws TimeoutException {
    log.debug("findByOrganizationIdAndLicenseId Correlation id: {}",
            1L);
    var license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
    return mapper.toModelFromEntity(license);
  }

  private License buildFallbackLicense(String organizationId, String licenseId,
                                                 Throwable t) {
    License license = new License();
    license.setLicenseId(licenseId);
    license.setOrganizationId(organizationId);
    license.setProductName(
            "Sorry no licensing information currently available");

    return license;
  }

  private List<License> buildFallbackLicenseList(String organizationId, Throwable t) {
    License license = new License();
    license.setLicenseId("0000000-00-00000");
    license.setOrganizationId(organizationId);
    license.setProductName("Sorry no licensing information currently available");
    List<License> fallbackList = new ArrayList<>();
    fallbackList.add(license);
    return fallbackList;
  }

  @Override
  public void save(License license) {
    licenseRepository.save(mapper.toEntityFromModel(license));
  }

  @Override
  public void delete(License license) {
    licenseRepository.delete(mapper.toEntityFromModel(license));
  }

  private void randomlyRunLong() throws TimeoutException {
    log.info("in randomlyRunLong");
    Random rand = new Random();
    int randomNum = rand.nextInt(3) + 1;
    if (randomNum == 3) {
      sleep();
    }
  }

  private void sleep() throws TimeoutException {
    try {
      Thread.sleep(5000);
      throw new java.util.concurrent.TimeoutException();
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
  }

}
