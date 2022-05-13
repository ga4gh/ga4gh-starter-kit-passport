package org.ga4gh.starterkit.passport.broker.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrokerProps {
    
    private String passportIssuer;
    private String brokerSecret;

    public BrokerProps() {
        setPassportIssuer("https://ga4gh.org/");
        setBrokerSecret("insecureSecretPleaseOverride");
    }
}
