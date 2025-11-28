FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the entire project
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/bill-extraction-api-1.0.0.jar"]
# Run the application
#ENTRYPOINT ["top", "-b"]