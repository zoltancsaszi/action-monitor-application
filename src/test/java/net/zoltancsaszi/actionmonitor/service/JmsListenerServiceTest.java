package net.zoltancsaszi.actionmonitor.service;

import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import org.apache.activemq.artemis.junit.EmbeddedActiveMQResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JmsListenerServiceTest {

    @Rule
    public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();

    @Autowired
    private JmsSenderService jmsSenderService;

    @Autowired
    private JmsListenerService jmsListenerService;

    @Test
    public void testReceive() throws Exception {
        jmsSenderService.send(new MonitoringEvent(0, 0, "Test EventType"));

        jmsListenerService.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(jmsListenerService.getLatch().getCount()).isEqualTo(0);
    }
}