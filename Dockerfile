FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
# Multi-stage build for Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage - use Java 21 JRE
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copy the built JAR from builder stage
COPY --from=builder /app/target/bill-extraction-api-*.jar app.jar

# Expose port
EXPOSE 8080

# JVM options for Render's free tier
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC"

# Health check (using wget since alpine doesn't have curl)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# Run the application
#ENTRYPOINT ["top", "-b"]