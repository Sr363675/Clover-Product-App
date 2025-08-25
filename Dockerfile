# Start from official JDK 17 runtime
FROM eclipse-temurin:17-jdk-alpine

# Set work directory inside container
WORKDIR /app

# Copy Maven wrapper & pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (caching layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Package the app
RUN ./mvnw clean package -DskipTests

# Run the app (replace with your jar name)
CMD ["java", "-jar", "target/Product-Clover-API-0.0.1-SNAPSHOT.jar"]
