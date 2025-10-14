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

# Set working directory
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=builder /build/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]