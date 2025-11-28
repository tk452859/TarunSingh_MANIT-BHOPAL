FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Copy entire project
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Create non-root user
RUN groupadd -r spring && useradd -r -g spring spring
RUN chown -R spring:spring /app
USER spring

EXPOSE 8080

# JVM options for Render
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/api/health || exit 1

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/bill-extraction-api-*.jar"]
#ENTRYPOINT ["top", "-b"]