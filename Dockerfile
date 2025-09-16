# Use official JDK 21 image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy the jar file from target folder
COPY target/ecom-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 5454

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
