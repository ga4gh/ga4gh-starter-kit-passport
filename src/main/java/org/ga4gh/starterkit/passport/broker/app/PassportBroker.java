/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.ga4gh.starterkit.passport.broker.app;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.util.webserver.ServerPropertySetter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.ga4gh.starterkit.passport.broker")
public class PassportBroker {
    
    public static void main(String[] args) {
        boolean setupSuccess = setup(args);
        if (setupSuccess) {
            SpringApplication.run(PassportBroker.class, args);
        } else {
            System.out.println("Application failed at initial setup phase, this is likely an error in the YAML config file. Exiting");
        }
    }

    private static boolean setup(String[] args) {
        Options options = new PassportBrokerSpringConfig().getCommandLineOptions();
        ServerPropertySetter setter = new ServerPropertySetter();
        return setter.setServerProperties(PassportBrokerYamlConfigContainer.class, args, options, "config");
    }
}
