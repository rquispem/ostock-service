package com.optimagrowth.infrastructure.adapters.input;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optimagrowth.application.ports.input.LicenseServicePort;
import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.config.ServiceConfig;
import com.optimagrowth.infrastructure.dto.api.request.CreateLicenseRequest;
import com.optimagrowth.infrastructure.dto.api.request.UpdateLicenseRequest;
import com.optimagrowth.infrastructure.dto.api.response.GetLicenseResponse;
import com.optimagrowth.infrastructure.mapper.LicenseMapper;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "v1/organization/{organizationId}/license")
public class LicenseController {
  private final LicenseServicePort licenseServicePort;
  private final LicenseMapper licenseMapper;
  private final MessageSource messageSource;
  private final ServiceConfig config;

  @Autowired
  public LicenseController(LicenseServicePort licenseServicePort,
                           LicenseMapper licenseMapper,
                           MessageSource messageSource,
                           ServiceConfig config) {
    this.licenseServicePort = licenseServicePort;
    this.licenseMapper = licenseMapper;
    this.messageSource = messageSource;
    this.config = config;
  }

  @GetMapping(value = "/{licenseId}/{clientType}")
  public ResponseEntity<GetLicenseResponse> getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                                                  @PathVariable("licenseId") String licenseId,
                                                                 @PathVariable("clientType") String clientType) {
    License license = licenseServicePort.getLicenseWithClientType(organizationId, licenseId, clientType);
    license.withComment(config.getProperty());
    GetLicenseResponse response = licenseMapper.ToGetLicenseResponse(license);
    return ResponseEntity.ok(response);

  }

  @GetMapping(value = "/{licenseId}")
  public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("organizationId") String organizationId,
                                                       @PathVariable("licenseId") String licenseId) {
    License license = licenseServicePort.getLicense(licenseId, organizationId);
    if (Objects.isNull(license)) {
      throw new IllegalArgumentException(
              String.format(messageSource.getMessage(
                              "license.search.error.message", null, null),
                      licenseId, organizationId));
    }
    license.withComment(config.getProperty());
    GetLicenseResponse response = licenseMapper.ToGetLicenseResponse(license);
    response.add(linkTo(methodOn(LicenseController.class)
                    .getLicense(organizationId, license.getLicenseId()))
                    .withSelfRel(),
            linkTo(methodOn(LicenseController.class)
                    .createLicense(licenseMapper.toCreateLicenseRequest(license)))
                    .withRel("createLicense"),
            linkTo(methodOn(LicenseController.class)
                    .updateLicense(licenseMapper.toUpdateLicenseRequest(license)))
                    .withRel("updateLicense"),
            linkTo(methodOn(LicenseController.class)
                    .deleteLicense(license.getLicenseId()))
                    .withRel("deleteLicense"));
    return ResponseEntity.ok(response);
  }

  @PutMapping
  public ResponseEntity<License> updateLicense(@RequestBody UpdateLicenseRequest request) {
    License license = licenseMapper.toLicenseFrom(request);
    license.withComment(config.getProperty());
    return ResponseEntity.ok(licenseServicePort.updateLicense(license));
  }

  @PostMapping
  public ResponseEntity<License> createLicense(@RequestBody CreateLicenseRequest request) {
    License license = licenseMapper.toLicenseFrom(request);
    license.withComment(config.getProperty());
    return ResponseEntity.ok(licenseServicePort.createLicense(license));
  }

  @DeleteMapping(value = "/{licenseId}")
  public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
    licenseServicePort.deleteLicense(licenseId);
    var responseMessage = String.format(messageSource.getMessage(
            "license.delete.message", null, null), licenseId);
    return ResponseEntity.ok(responseMessage);
  }
}
