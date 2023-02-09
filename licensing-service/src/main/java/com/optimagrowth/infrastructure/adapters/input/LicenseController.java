package com.optimagrowth.infrastructure.adapters.input;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optimagrowth.domain.License;
import com.optimagrowth.infrastructure.dto.CreateLicenseRequest;
import com.optimagrowth.infrastructure.dto.GetLicenseResponse;
import com.optimagrowth.infrastructure.dto.UpdateLicenseRequest;
import com.optimagrowth.infrastructure.mapper.LicenseMapper;
import com.optimagrowth.infrastructure.ports.input.LicenseServicePort;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "v1/organization/{organizationId}/license")
public class LicenseController {
  private final LicenseServicePort licenseServicePort;
  private final LicenseMapper licenseMapper;

  @Autowired
  public LicenseController(LicenseServicePort licenseServicePort,
                           LicenseMapper licenseMapper) {
    this.licenseServicePort = licenseServicePort;
    this.licenseMapper = licenseMapper;
  }

  @GetMapping(value = "/{licenseId}")
  public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("organizationId") String organizationId,
                                                       @PathVariable("licenseId") String licenseId) {
    License license = licenseServicePort.getLicense(licenseId, organizationId);
    GetLicenseResponse response = licenseMapper.ToGetLicenseResponse(license);
    response.add(linkTo(methodOn(LicenseController.class)
                    .getLicense(organizationId, license.getLicenseId()))
                    .withSelfRel(),
            linkTo(methodOn(LicenseController.class)
                    .createLicense(organizationId, licenseMapper.toCreateLicenseRequest(license), null))
                    .withRel("createLicense"),
            linkTo(methodOn(LicenseController.class)
                    .updateLicense(organizationId, licenseMapper.toUpdateLicenseRequest(license), null))
                    .withRel("updateLicense"),
            linkTo(methodOn(LicenseController.class)
                    .deleteLicense(organizationId, license.getLicenseId()))
                    .withRel("deleteLicense"));
    return ResponseEntity.ok(response);
  }

  @PutMapping
  public ResponseEntity<String> updateLicense(@PathVariable("organizationId") String organizationId,
                                              @RequestBody UpdateLicenseRequest request,
                                              @RequestHeader(value = "Accept-Language", required = false)
                                                Locale locale) {
    License license = licenseMapper.toLicenseFrom(request);
    return ResponseEntity.ok(licenseServicePort.updateLicense(license, organizationId, locale));
  }

  @PostMapping
  public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId,
                                              @RequestBody CreateLicenseRequest request,
                                              @RequestHeader(value = "Accept-Language", required = false)
                                              Locale locale) {
    License license = licenseMapper.toLicenseFrom(request);
    return ResponseEntity.ok(licenseServicePort.createLicense(license, organizationId, locale));
  }

  @DeleteMapping(value = "/{licenseId}")
  public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                              @PathVariable("licenseId") String licenseId) {
    return ResponseEntity.ok(licenseServicePort.deleteLicense(licenseId, organizationId));
  }
}
