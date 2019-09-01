package net.zoltancsaszi.actionmonitor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * Configuration component responsible for setting up ActiveMQ Artemis.
 *
 * @author Zoltan Csaszi
 */
@Configuration
@EnableJms
public class ArtemisConfig {

    @Bean
    public ArtemisConfigurationCustomizer customizer(@Autowired ArtemisProperties artemisProperties) {
        return configuration -> {
            try {
                configuration.addAcceptorConfiguration("netty", "tcp://localhost:" + artemisProperties.getPort());
            } catch (Exception e) {
                throw new RuntimeException("Failed to add netty transport acceptor to artemis instance", e);
            }
        };
    }
}
