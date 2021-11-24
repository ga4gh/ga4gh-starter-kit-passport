package org.ga4gh.starterkit.passport.app;

import org.ga4gh.starterkit.common.config.ContainsServerProps;
import org.ga4gh.starterkit.common.config.ServerProps;

public class PassportServerYamlConfigContainer implements ContainsServerProps {

    private PassportServerYamlConfig starterKitPassport;

    public PassportServerYamlConfigContainer() {
        starterKitPassport = new PassportServerYamlConfig();
    }

    public void setStarterKitPassport(PassportServerYamlConfig starterKitPassport) {
        this.starterKitPassport = starterKitPassport;
    }

    public PassportServerYamlConfig getStarterKitPassport() {
        return starterKitPassport;
    }

    public ServerProps getServerProps() {
        return getStarterKitPassport().getServerProps();
    }
}
