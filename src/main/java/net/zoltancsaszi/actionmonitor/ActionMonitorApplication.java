package net.zoltancsaszi.actionmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a standalone Spring Boot application that monitors insert and update actions on a database table,
 * using JMS, WebSocket and REST technologies, containing an embedded Tomcat application server and an ActiveMQ Artemis.
 *
 * @author Zoltan Csaszi
 */
@SpringBootApplication
public class ActionMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActionMonitorApplication.class, args);
    }

}
