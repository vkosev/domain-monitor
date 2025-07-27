# domain-monitor

Spring Boot SSL Certficiates Expiration Monitor System & REST API

#### Java Version - 21.0.05
#### Spring Boot Version - 3.5.4

## How to setup and start locally
1. Run PostgreSQL database
    - There is **Docker Compose File** located in ***/src/main/java/resources***
      <br> To create and start docker container image use command `docker compose up -d` 
    - If you have PostgreSQL installed locally, then create database. If you chose this option 
      <br>edit the **application.properties** file accordingly
2. Install Maven
3. Start the Spring Boot app.
   - You can start project by building with Maven:
     <br> From root folder, build jar file using Maven - ```mvn clean package -DskipTests```
     <br> Run the jar file ```java -jar target/domain-monitor-0.0.1-SNAPSHOT.jar```
   - You can also just open the project in your favourite IDE and start it from there. 

### Important application properties
In the project application.properties file you will find the following important properties:
* **domain.thresholds** - specify comma separated integers, representing 
  <br>the thresholds(in days) for when send notifications. Default is 7,30,60 days
* **scheduler.time** - Spring CRON expression, for when the scheduled job to be triggered.
  <br> currently default to `0 0 */1 * * *` which means every hour

#### Swagger documentation available at ***http://localhost:8080/api/v1/docs***
#### AWS Lambda Notification Function available at ***https://github.com/vkosev/domain-monitor-lambda-notifier***

There is simple golang web-server that listens for notification at 127.0.0.1:8090/notifications 
<br> use it to test the web-hooks feature
