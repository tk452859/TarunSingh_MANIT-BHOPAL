FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
# Multi-stage build for Java 21
# Build stage
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN apk add --no-cache maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENV PORT=8080
EXPOSE $PORT
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
# Run the application
#ENTRYPOINT ["top", "-b"]