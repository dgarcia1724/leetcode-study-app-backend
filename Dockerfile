# Use OpenJDK 21 as the base image for building
FROM openjdk:21-jdk-slim as builder

# Set the working directory
WORKDIR /app

# Copy the Maven configuration and source code
COPY pom.xml .
COPY .mvn .mvn
COPY src ./src
COPY mvnw .
COPY mvnw.cmd .

# Install necessary tools
RUN apt-get update && apt-get install -y dos2unix

# Ensure line endings are correct and grant execution permission
RUN dos2unix mvnw && chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a lighter image for runtime
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy the built jar file
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
