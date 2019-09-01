package net.zoltancsaszi.actionmonitor.configuration;

import net.zoltancsaszi.actionmonitor.messaging.JdbcMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * Configuration component responsible for Spring Integration setup.
 * Polling periodically the AuditLog table in the database if there
 * is a new insert or update action, then create messages from the
 * recent actions, that handled by the JdbcMessageHandler.
 *
 * @author Zoltan Csaszi
 */
@Configuration
@EnableIntegration
@Profile("default")
public class SpringIntegrationConfiguration {

    @Autowired
    private DataSource dataSource;

    @Value("${database.poller.period}")
    private long pollingFrequency;

    @Bean
    public DirectChannel fromDBChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageSource<Object> jdbcMessageSource() {
        JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(
                dataSource, "SELECT ID, PRIM_KEY, TIMESTAMP_, " +
                "EVENT_TYPE FROM AuditLog WHERE IS_NEW = 1");

        jdbcPollingChannelAdapter.setUpdateSql("UPDATE AuditLog SET IS_NEW = 0 WHERE ID in (:ID)");
        jdbcPollingChannelAdapter.setUpdatePerRow(true);

        return jdbcPollingChannelAdapter;
    }

    @Bean
    public IntegrationFlow integrationFlow(@Autowired  JdbcMessageHandler jdbcMessageHandler) {
        return IntegrationFlows
                .from(
                        jdbcMessageSource(),
                        c -> c.poller(Pollers.fixedRate(pollingFrequency, TimeUnit.MILLISECONDS)))
                .channel(fromDBChannel())
                .handle(jdbcMessageHandler)
                .get();
    }

}