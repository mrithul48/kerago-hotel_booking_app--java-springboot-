# --- STAGE 1: Build the Application with JDK 21 ---

# Use Gradle image with JDK 21 to build the app
FROM gradle:8.8-jdk21 AS builder

# Set working directory
WORKDIR /build

# Copy gradle configuration files and project source
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# Build the application (skip tests for faster build)
RUN gradle clean build -x test --stacktrace

# --- STAGE 2: Create the Final Lightweight Image with JRE 21 ---

FROM openjdk:21-slim

# Install necessary network tools for debugging (optional, can remove in production)
RUN apt-get update && apt-get install -y \
    iputils-ping \
    telnet \
    curl \
    dnsutils \
    netcat-traditional \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=builder /build/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application with Java network debugging (remove after fixing)
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "app.jar"]