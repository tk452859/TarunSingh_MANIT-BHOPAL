FROM ubuntu:latest
LABEL authors="tk452"
# Simple build - copies source and builds in one stage
# Multi-stage build for Java 21
FROM maven:4.0.0-rc-4

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Debug: Show what was built
RUN find . -name "*.jar" -type f

CMD ["java", "-jar", "target/bill-extractor-0.0.1-SNAPSHOT.jar"]

# Run the application
#ENTRYPOINT ["top", "-b"]