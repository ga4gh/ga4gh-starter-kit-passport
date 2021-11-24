package org.ga4gh.starterkit.passport.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.ga4gh.starterkit.common.model.ServiceInfo;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.ID;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.NAME;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.DESCRIPTION;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.CONTACT_URL;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.DOCUMENTATION_URL;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.CREATED_AT;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.UPDATED_AT;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.ENVIRONMENT;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.VERSION;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.ORGANIZATION_NAME;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.ORGANIZATION_URL;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.SERVICE_TYPE_GROUP;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.SERVICE_TYPE_ARTIFACT;
import static org.ga4gh.starterkit.passport.constant.PassportServiceInfoDefaults.SERVICE_TYPE_VERSION;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class PassportServiceInfo extends ServiceInfo {

    public PassportServiceInfo() {
        super();
        setAllDefaults();
    }

    private void setAllDefaults() {
        setId(ID);
        setName(NAME);
        setDescription(DESCRIPTION);
        setContactUrl(CONTACT_URL);
        setDocumentationUrl(DOCUMENTATION_URL);
        setCreatedAt(CREATED_AT);
        setUpdatedAt(UPDATED_AT);
        setEnvironment(ENVIRONMENT);
        setVersion(VERSION);
        getOrganization().setName(ORGANIZATION_NAME);
        getOrganization().setUrl(ORGANIZATION_URL);
        getType().setGroup(SERVICE_TYPE_GROUP);
        getType().setArtifact(SERVICE_TYPE_ARTIFACT);
        getType().setVersion(SERVICE_TYPE_VERSION);
    }
    
}
