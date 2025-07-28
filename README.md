# domain-monitor

Spring Boot SSL Certficiates Expiration Monitor System & REST API

#### Java Version - 21.0.05 <br> Spring Boot Version - 3.5.4

---
### AWS Deployment
**API Public Access - http://3.65.60.16:8080**<br>
**Public Swagger Documentation - http://3.65.60.16:8080/api/v1/docs**
---
#### AWS Lambda Notification Function available at ***https://github.com/vkosev/domain-monitor-lambda-notifier***

---

## How to setup and start locally
1. Run PostgreSQL database
    - There is **Docker Compose File** located in ***/src/main/java/resources/docker***
      <br> To create and start docker container image use command `docker compose up -d` 
    - If you have PostgreSQL installed locally, then create database. If you chose this option 
      <br>edit the **application.properties** file accordingly
2. Install Maven
3. Start the Spring Boot app.
   - You can start project by building with Maven:
     <br> From root folder, build jar file using Maven - ```mvn clean package -DskipTests```
     <br> Run the jar file ```java -jar target/domain-monitor-0.0.1-SNAPSHOT.jar```
   - You can also just open the project in your favourite IDE and start it from there. 
---
### Important Notice
In the project application.properties file you will find the following important properties:
* **domain.thresholds** - specify comma separated integers, representing 
  <br>the thresholds(in days) for when to send notifications. Default is 7,30,60 days
* **scheduler.time** - Spring CRON expression, for when the scheduled job to be triggered.
  <br> currently defaults to `0 0 */1 * * *` which means every hour

#### Swagger documentation available at ***http://localhost:8080/api/v1/docs***

There is simple golang web-server that listens for notification at 127.0.0.1:8090/notifications
<br>The test server executable is located inside **_test_server/server.exe_**
<br>Use it to test the web-hooks feature
---

## Application Documentation
### Architecture 
The RESTful API is built using architecture called Hexagonal Architecture aka Ports & Adapters Architecture.

The idea is that you have a core(domain) layer where you have the constructs of the domain and logic of how this domain behaves. For any other dependencies you expose ports (java interfaces).

All other layers of the application must conform to the defined ports if they want to use the domain layer and implement an adapter(implementation of the interfaces) for the specific ports.

For example the persistence layer must implement the Repository interfaces defined in the domain layer if it wants to persist domain objects.


All layers are allowed to know about the domain layer(import classes from the domain package), but are not allowed to know about the other layers(can’t import classes outside the domain).

The domain layer must not have any external dependencies, no spring or other external packages imports.

---
### Infrastructure Overview
The application is using AWS for the infrastructure.
There you have:
- ECR to publish docker images
- ECS using Fargate to deploy and run the image
- RDS Postgres for the database
- SNS with defined notification topic where for each received message it notifies by email the registered emails
- Lambda function that checks for expired certifications and sends messages to SNS if any expired certificates exist
- Cloudwatch Log Groups to monitor the application and lambda executions
- Configured outbound and inbound rules so that the services can communicate with each other.

For the resources orchestration there is Cloudformation Template inside the **/aws_infra** folder.

---

### What’s Done?
1. Add domains for which to track the expirations <br> `POST host:8080/api/v1/domains/add`
2. Delete added domains <br> `DELETE host:8080/api/v1/domains/delete/{domain}`
3. Add a webhook where the application will notify each registered source if any of the domain certificates exceeds the defined threshold days.
<br> `POST host:8080/api/v1/hook/registe`
4. Manual trigger for expiration check of the added domains <br> `GET host:8080/api/v1/domains/check-certificates`
5. Query historical records for every expiration check that’s been done <br> `GET host:8080/api/v1/domains/history/records`

**Important! when adding domains, do not include _http_ or _https_ prefix**

**The application also includes:**
- Scheduled job that checks for expired certificates and sends notification to all registered hooks.
- Swagger documentation
- Structured Logging
- Possibility to configure threshold days using environment variable
- Possibility to configure when the scheduler job to run using environment variable

---

### What’s NOT Done?
There is no unit or integration tests for the API
<br>There is no async processing for bulk domain checks
<br>There is no pipelines configured for CI/CD flow
<br>There is no CloudWatch Logs triggers

---






