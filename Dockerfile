# Use a base image with Java and Maven installed
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the dependencies specified in the pom.xml file
RUN mvn dependency:go-offline -B

# Copy the source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image with Java installed
FROM adoptopenjdk:11-jre-hotspot AS runtime

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage to the runtime stage
COPY --from=build /app/target/bizum-0.0.1.jar .

# Expose the port on which the application will run
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "bizum-0.0.1.jar"]
