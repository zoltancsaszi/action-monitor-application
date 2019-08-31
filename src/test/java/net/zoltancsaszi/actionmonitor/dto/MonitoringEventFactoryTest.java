package net.zoltancsaszi.actionmonitor.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MonitoringEventFactoryTest {

    @InjectMocks
    private MonitoringEventFactory monitoringEventFactory;

    @Test
    public void createMonitoringEntityTest() {

        // given the properties of a database insert event

        long id = 1;
        long timestamp = System.currentTimeMillis();
        String eventType = "inserted";

        // when we call monitoringEventFactory's createMonitoringEvent method

        MonitoringEvent monitoringEvent =
                monitoringEventFactory.createMonitoringEvent(id, timestamp, eventType);

        // then the monitoringEvent created with the right payload

        assertThat(monitoringEvent, notNullValue());

        String payload = monitoringEvent.getPayload();

        assertThat(payload, notNullValue());
        assertThat(payload, equalTo("timestamp=" + timestamp + " :: a row with ID=1 was inserted"));
    }
}