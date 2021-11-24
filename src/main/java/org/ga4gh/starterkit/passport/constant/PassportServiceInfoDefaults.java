package org.ga4gh.starterkit.passport.constant;

import java.time.LocalDateTime;

import org.ga4gh.starterkit.common.constant.DateTimeConstants;

public class PassportServiceInfoDefaults {
    public static final String ID = "org.ga4gh.starterkit.passport";
    public static final String NAME = "GA4GH Starter Kit Passport Service";
    public static final String DESCRIPTION = "Starter Kit reference implementation"
        + " of a GA4GH Passport Broker service according to the "
        + "Passport specification";
    public static final String CONTACT_URL = "mailto:info@ga4gh.org";
    public static final String DOCUMENTATION_URL = "https://starterkit.ga4gh.org";
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2021-11-24T12:00:00Z", DateTimeConstants.DATE_FORMATTER);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.parse("2021-11-24T12:00:00Z", DateTimeConstants.DATE_FORMATTER);
    public static final String ENVIRONMENT = "test";
    public static final String VERSION = "0.1.0";
    public static final String ORGANIZATION_NAME = "Global Alliance for Genomics and Health";
    public static final String ORGANIZATION_URL = "https://ga4gh.org";
    public static final String SERVICE_TYPE_GROUP = "org.ga4gh";
    public static final String SERVICE_TYPE_ARTIFACT = "passport";
    public static final String SERVICE_TYPE_VERSION = "1.0.0";
}
