FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
# Multi-stage build for Java 21
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
ENV PORT=8080
EXPOSE $PORT

# Install basic tools for health check
RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]

# Run the application
#ENTRYPOINT ["top", "-b"]