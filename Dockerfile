# --------- Build Stage ---------
FROM maven:3.9.7-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# --------- Run Stage (JRE only, not JDK) ---------
FROM eclipse-temurin:21-jre as runtime

# App user for security (not root)
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Reduce attack surface: use a non-root user
USER appuser

# JVM options for performance and memory (edit as needed)
ENV JAVA_OPTS="-XX:+UseContainerSupport"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
