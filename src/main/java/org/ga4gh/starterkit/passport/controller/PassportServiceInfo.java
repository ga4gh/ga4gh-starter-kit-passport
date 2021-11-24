package org.ga4gh.starterkit.passport.controller;

import org.ga4gh.starterkit.common.model.ServiceInfo;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ga4gh/passport/v1/service-info")
public class PassportServiceInfo {

    @Autowired
    private org.ga4gh.starterkit.passport.model.PassportServiceInfo passportServiceInfo;

    @Autowired
    private LoggingUtil loggingUtil;

    @GetMapping
    public org.ga4gh.starterkit.passport.model.PassportServiceInfo getServiceInfo() {
        return passportServiceInfo;
    }
    
}
