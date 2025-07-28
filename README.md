# domain-monitor

Spring Boot **SSL Certificates Expiration Monitor System** & REST API

**Java Version:** 21.0.05  
**Spring Boot Version:** 3.5.4

---

### AWS Deployment

**API Public Access - http://3.65.60.16:8080**

**Public Swagger Documentation - http://3.65.60.16:8080/api/v1/docs**

---

#### AWS Lambda Notification Function available at [https://github.com/vkosev/domain-monitor-lambda-notifier](https://github.com/vkosev/domain-monitor-lambda-notifier)

---

## How to Setup and Start Locally

1. **Run PostgreSQL Database**
    - There is a **Docker Compose File** located at `src/main/resources/docker/docker-compose.yml` <br>To create and start the Docker container, run:  
      ```bash
      docker compose up -d
      ```
    - If you have PostgreSQL installed locally, create the database manually and edit the `application.properties` file accordingly.

2. **Install Maven**

3. **Start the Spring Boot App**
    - Build the jar file using Maven:  
      ```bash
      mvn clean package -DskipTests
      ```
    - Run the jar file:  
      ```bash
      java -jar target/domain-monitor-0.0.1-SNAPSHOT.jar
      ```
    - Alternatively, open the project in your favourite IDE and start it from there.

---

### Important Notice

In the project's `application.properties` file, you will find the following important properties:

- **`domain.thresholds`**  
  Specify comma-separated integers representing the thresholds (in days) for when to send notifications.  
  _Default is 7, 30, 60 days._

- **`scheduler.time`**  
  Spring CRON expression for when the scheduled job will be triggered.  
  _Defaults to:_ `0 0 */1 * * *` (every hour)

#### Swagger documentation available at [http://localhost:8080/api/v1/docs](http://localhost:8080/api/v1/docs)

There is a simple Golang web server that listens for notifications at `127.0.0.1:8090/notifications`.  
The test server executable is located inside `test_server/server.exe`.  
Use it to test the web-hooks feature.

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

The application uses AWS for the infrastructure:

- **ECR** to publish Docker images
- **ECS (Fargate)** to deploy and run the image
- **RDS Postgres** as the database
- **SNS** for notification topics (email notifications)
- **Lambda** for certificate expiration checks and SNS notifications
- **CloudWatch Log Groups** for monitoring
- Configured inbound/outbound rules for inter-service communication
- **CloudFormation template** for orchestration, found in `/aws_infra`

---

### What’s Done?

1. Add domains to track for expiration  
   `POST /api/v1/domains/add`
2. Delete added domains  
   `DELETE /api/v1/domains/delete/{domain}`
3. Add a webhook for notifications  
   `POST /api/v1/hook/register`
4. Manual trigger for expiration check  
   `GET /api/v1/domains/check-certificates`
5. Query historical records  
   `GET /api/v1/domains/history/records`

**Note:** When adding domains, _do not include `http` or `https` prefix_.

**The application also includes:**

- Scheduled job to check for expired certificates and notify hooks
- Swagger documentation
- Structured logging
- Configurable threshold days (env variable)
- Configurable scheduler cron (env variable)

---

### What’s NOT Done?

- No unit or integration tests for the API
- No async processing for bulk domain checks
- No CI/CD pipelines configured
- No CloudWatch Logs triggers

---
