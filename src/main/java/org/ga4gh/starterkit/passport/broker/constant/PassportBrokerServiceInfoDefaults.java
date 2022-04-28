package org.ga4gh.starterkit.passport.broker.constant;

import java.time.LocalDateTime;
import org.ga4gh.starterkit.common.constant.DateTimeConstants;

public class PassportBrokerServiceInfoDefaults {
    public static final String ID = "org.ga4gh.starterkit.passport.broker";
    public static final String NAME = "GA4GH Starter Kit Passport Broker Service";
    public static final String DESCRIPTION = "Starter Kit implementation of a"
        + " Passport Broker service, outlined in the GA4GH Passports"
        + " specification. Manages researcher permissions to data and compute,"
        + " and enables this information to be minted as JWTs and passed to"
        + " downstream clearinghouses.";
    public static final String CONTACT_URL = "mailto:info@ga4gh.org";
    public static final String DOCUMENTATION_URL = "https://github.com/ga4gh/ga4gh-starter-kit-passport-broker";
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2022-04-28T09:00:00Z", DateTimeConstants.DATE_FORMATTER);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.parse("2022-04-28T09:00:00Z", DateTimeConstants.DATE_FORMATTER);
    public static final String ENVIRONMENT = "test";
    public static final String VERSION = "0.1.0";
    public static final String ORGANIZATION_NAME = "Global Alliance for Genomics and Health";
    public static final String ORGANIZATION_URL = "https://ga4gh.org";
    public static final String SERVICE_TYPE_GROUP = "org.ga4gh";
    public static final String SERVICE_TYPE_ARTIFACT = "passport-broker";
    public static final String SERVICE_TYPE_VERSION = "1.0.0";
}
