# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Run maven clean install to build the application
RUN mvn clean install -DskipTests

# Stage 2: Package the application
FROM openjdk:17
WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8090

# Command to run the application
CMD ["java", "-jar", "app.jar"]