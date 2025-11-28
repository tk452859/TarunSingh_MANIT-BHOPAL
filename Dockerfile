FROM ubuntu:latest
LABEL authors="tk452"
# Use official OpenJDK 17 base image
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Create a non-root user for security (important for Render)
RUN groupadd -r spring && useradd -r -g spring spring

# Copy the JAR file from your build (you'll need to build it first)
COPY target/bill-extraction-api-*.jar app.jar

# Change ownership to spring user
RUN chown -R spring:spring /app
USER spring

# Expose port (Render uses port 10000 by default, but we'll use 8080)
EXPOSE 8080

# Set JVM options optimized for Render's free tier
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:MaxRAMPercentage=75.0"

# Health check endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/api/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
#ENTRYPOINT ["top", "-b"]