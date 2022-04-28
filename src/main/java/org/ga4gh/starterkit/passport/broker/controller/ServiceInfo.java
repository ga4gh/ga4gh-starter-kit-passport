package org.ga4gh.starterkit.passport.broker.controller;

import org.ga4gh.starterkit.passport.broker.model.PassportBrokerServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ga4gh/passport/v1/service-info")
public class ServiceInfo {

    @Autowired
    private PassportBrokerServiceInfo serviceInfo;

    @GetMapping
    public PassportBrokerServiceInfo getServiceInfo() {
        return serviceInfo;
    }
    
}
