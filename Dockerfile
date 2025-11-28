FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
# Multi-stage build for Java 21
FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Debug: Show what was built
RUN find . -name "*.jar" -type f

CMD ["java", "-jar", "target/bill-extraction-api-1.0.0.jar"]

# Run the application
#ENTRYPOINT ["top", "-b"]