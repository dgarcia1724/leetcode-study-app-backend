# Use a base image that includes JDK
FROM openjdk:17-jdk-slim as builder

# Set the working directory in the container
WORKDIR /app

# Convert line endings to LF
RUN apt-get update && apt-get install -y dos2unix

# Copy the Maven pom.xml and other necessary files to build the project
COPY pom.xml .
COPY src ./src
COPY mvnw .

RUN dos2unix mvnw
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Second stage: create a smaller image for the runtime
FROM openjdk:17-slim


# Set the working directory
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/leetcode_study_app_backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
