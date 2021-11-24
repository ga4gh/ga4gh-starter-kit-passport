package org.ga4gh.starterkit.passport.app;

import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.passport.model.PassportServiceInfo;

public class PassportServerYamlConfig {

    private ServerProps serverProps;
    private DatabaseProps databaseProps;
    private PassportServiceInfo serviceInfo;

    public PassportServerYamlConfig() {
        serverProps = new ServerProps();
        databaseProps = new DatabaseProps();
        serviceInfo = new PassportServiceInfo();
    }

    public void setServerProps(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    public ServerProps getServerProps() {
        return serverProps;
    }

    public void setDatabaseProps(DatabaseProps databaseProps) {
        this.databaseProps = databaseProps;
    }

    public DatabaseProps getDatabaseProps() {
        return databaseProps;
    }

    public void setServiceInfo(PassportServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public PassportServiceInfo getServiceInfo() {
        return serviceInfo;
    }
}
