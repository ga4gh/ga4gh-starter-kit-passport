package org.ga4gh.starterkit.passport.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.catalina.connector.Connector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsConnector;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsFilter;
import org.ga4gh.starterkit.common.util.webserver.CorsFilterBuilder;
import org.ga4gh.starterkit.common.util.webserver.TomcatMultiConnectorServletWebServerFactoryCustomizer;
import org.ga4gh.starterkit.passport.model.PassportServiceInfo;
import org.ga4gh.starterkit.passport.model.PassportUser;
import org.ga4gh.starterkit.passport.model.PassportVisa;
import org.ga4gh.starterkit.passport.utils.hibernate.PassportHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties
public class PassportServerSpringConfig implements WebMvcConfigurer {

    /* ******************************
     * TOMCAT SERVER
     * ****************************** */

    @Value("${server.admin.port:4501}")
    private String serverAdminPort;

    @Bean
    public WebServerFactoryCustomizer servletContainer() {
        Connector[] additionalConnectors = AdminEndpointsConnector.additionalConnector(serverAdminPort);
        ServerProperties serverProperties = new ServerProperties();
        return new TomcatMultiConnectorServletWebServerFactoryCustomizer(serverProperties, additionalConnectors);
    }

    @Bean
    public FilterRegistrationBean<AdminEndpointsFilter> adminEndpointsFilter() {
        return new FilterRegistrationBean<AdminEndpointsFilter>(new AdminEndpointsFilter(Integer.valueOf(serverAdminPort)));
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(
        @Autowired ServerProps serverProps
    ) {
        return new CorsFilterBuilder(serverProps).buildFilter();
    }

    /* ******************************
     * YAML CONFIG
     * ****************************** */

    @Bean
    public Options getCommandLineOptions() {
        final Options options = new Options();
        options.addOption("c", "config", true, "Path to YAML config file");
        return options;
    }

    @Bean
    @Scope(PassportServerConstants.PROTOTYPE)
    @Qualifier(PassportServerConstants.EMPTY_PASSPORT_CONFIG_CONTAINER)
    public PassportServerYamlConfigContainer emptyPassportConfigContainer() {
        return new PassportServerYamlConfigContainer();
    }

    @Bean
    @Qualifier(PassportServerConstants.DEFAULT_PASSPORT_CONFIG_CONTAINER)
    public PassportServerYamlConfigContainer defaultPassportConfigContainer(
        @Qualifier(PassportServerConstants.EMPTY_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer passportConfigContainer
    ) {
        return passportConfigContainer;
    }

    @Bean
    @Qualifier(PassportServerConstants.USER_PASSPORT_CONFIG_CONTAINER)
    public PassportServerYamlConfigContainer userPassportConfigContainer(
        @Autowired ApplicationArguments args,
        @Autowired() Options options,
        @Qualifier(PassportServerConstants.EMPTY_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer passportConfigContainer
    ) {

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args.getSourceArgs());
            String configFilePath = cmd.getOptionValue("config");
            if (configFilePath != null) {
                File configFile = new File(configFilePath);
                if (configFile.exists() && !configFile.isDirectory()) {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    passportConfigContainer = mapper.readValue(configFile, PassportServerYamlConfigContainer.class);
                } else {
                    throw new FileNotFoundException();
                }
            }
        } catch (ParseException e) {
            System.out.println("ERROR: problem encountered setting config, config not set");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: problem encountered setting config, config file not found");
        } catch (IOException e) {
            System.out.println("ERROR: problem encountered setting config, config YAML could not be parsed");
        }

        return passportConfigContainer;
    }

    @Bean
    @Qualifier(PassportServerConstants.FINAL_PASSPORT_CONFIG_CONTAINER)
    public PassportServerYamlConfigContainer finalPassportConfigContainer(
        @Qualifier(PassportServerConstants.DEFAULT_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer defaultContainer,
        @Qualifier(PassportServerConstants.USER_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer userContainer
    ) {
        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(userContainer, defaultContainer);
        return defaultContainer;
    }

    @Bean
    public ServerProps getServerProps(
        @Qualifier(PassportServerConstants.FINAL_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer passportConfigContainer
    ) {
        return passportConfigContainer.getStarterKitPassport().getServerProps();
    }

    @Bean
    public DatabaseProps getDatabaseProps(
        @Qualifier(PassportServerConstants.FINAL_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer passportConfigContainer
    ) {
        return passportConfigContainer.getStarterKitPassport().getDatabaseProps();
    }

    @Bean
    public PassportServiceInfo getServiceInfo(
        @Qualifier(PassportServerConstants.FINAL_PASSPORT_CONFIG_CONTAINER) PassportServerYamlConfigContainer passportConfigContainer
    ) {
        return passportConfigContainer.getStarterKitPassport().getServiceInfo();
    }

    /* ******************************
     * LOGGING
     * ****************************** */

    @Bean
    public LoggingUtil loggingUtil() {
        return new LoggingUtil();
    }

    /* ******************************
     * HIBERNATE CONFIG
     * ****************************** */

    @Bean
    public PassportHibernateUtil getPassportHibernateUtil(
        @Autowired DatabaseProps databaseProps
    ) {
        PassportHibernateUtil hibernateUtil = new PassportHibernateUtil();
        hibernateUtil.setDatabaseProps(databaseProps);
        return hibernateUtil;
    }

    /* ******************************
     * REQUEST HANDLER
     * ****************************** */

    // USERS

    @Bean
    @RequestScope
    public BasicShowRequestHandler<String, PassportUser> showUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicShowRequestHandler<String, PassportUser> showUser = new BasicShowRequestHandler<>(PassportUser.class);
        showUser.setHibernateUtil(hibernateUtil);
        return showUser;
    }

    @Bean
    @RequestScope
    public BasicCreateRequestHandler<String, PassportUser> createUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicCreateRequestHandler<String, PassportUser> createUser = new BasicCreateRequestHandler<>(PassportUser.class);
        createUser.setHibernateUtil(hibernateUtil);
        return createUser;
    }

    @Bean
    @RequestScope
    public BasicUpdateRequestHandler<String, PassportUser> updateUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicUpdateRequestHandler<String, PassportUser> updateUser = new BasicUpdateRequestHandler<>(PassportUser.class);
        updateUser.setHibernateUtil(hibernateUtil);
        return updateUser;
    }

    @Bean
    @RequestScope
    public BasicDeleteRequestHandler<String, PassportUser> deleteUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicDeleteRequestHandler<String, PassportUser> deleteUser = new BasicDeleteRequestHandler<>(PassportUser.class);
        deleteUser.setHibernateUtil(hibernateUtil);
        return deleteUser;
    }

    // VISAS

    @Bean
    @RequestScope
    public BasicShowRequestHandler<String, PassportVisa> showVisaRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicShowRequestHandler<String, PassportVisa> showVisa = new BasicShowRequestHandler<>(PassportVisa.class);
        showVisa.setHibernateUtil(hibernateUtil);
        return showVisa;
    }

    @Bean
    @RequestScope
    public BasicCreateRequestHandler<String, PassportVisa> createVisaRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicCreateRequestHandler<String, PassportVisa> createVisa = new BasicCreateRequestHandler<>(PassportVisa.class);
        createVisa.setHibernateUtil(hibernateUtil);
        return createVisa;
    }

    @Bean
    @RequestScope
    public BasicUpdateRequestHandler<String, PassportVisa> updateVisaRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicUpdateRequestHandler<String, PassportVisa> updateVisa = new BasicUpdateRequestHandler<>(PassportVisa.class);
        updateVisa.setHibernateUtil(hibernateUtil);
        return updateVisa;
    }

    @Bean
    @RequestScope
    public BasicDeleteRequestHandler<String, PassportVisa> deleteVisaRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicDeleteRequestHandler<String, PassportVisa> deleteVisa = new BasicDeleteRequestHandler<>(PassportVisa.class);
        deleteVisa.setHibernateUtil(hibernateUtil);
        return deleteVisa;
    }
}