# Action-Monitor Application

This is a standalone Spring Boot application that monitors insert and update actions on a database table, using JMS, WebSocket and REST technologies, containing an embedded Tomcat application server and an ActiveMQ Artemis.

## How to run
* Clone the repository from Github
`git clone https://github.com/zoltancsaszi/action-monitor-application.git`
* Be sure to have maven installed `https://maven.apache.org/download.cgi`
* Build the application from the local directory
`mvn clean install`
* Run the application with the following command `mvn spring-boot:run` or `java -jar target/action-monitor-1.0.0-SNAPSHOT`

## How to use

1. Set up the monitoring environment
    1. Open http://localhost:8081 url in a web browser.
    2. Click on the `Connect` button.

2. Insert and update records to the embedded database. You can do this via
    * H2 Console UI `http://localhost:8081/h2-console`
        * Insert or update records to the `Wallet` table
    * REST API
        * Insert Wallet record: `POST http://localhost:8081/api/wallets` with JSON 
            ```
            {
                "amount": 100.0,
                "name": "John"
            }
            ```
        * Update Wallet record: `POST http://localhost:8081/api/wallets/{id}` with JSON 
          ```
          {
              "amount": 103.0,
              "name": "John"
          }
          ```
     Database actions will appear in the browser.
     
### Application Info queries
* Version info request: `GET http://localhost:8081/api/version`
* Application availability: `GET http://localhost:8081/api/availability`
 
