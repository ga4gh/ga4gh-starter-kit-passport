package org.ga4gh.starterkit.passport.broker.app;

import org.ga4gh.starterkit.common.config.ContainsServerProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PassportBrokerYamlConfigContainer implements ContainsServerProps {

    private PassportBrokerYamlConfig passportBrokerConfig;

    public PassportBrokerYamlConfigContainer() {
        passportBrokerConfig = new PassportBrokerYamlConfig();
    }

    public ServerProps getServerProps() {
        return getPassportBrokerConfig().getServerProps();
    }
}
