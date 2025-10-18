# 1️⃣ Use official OpenJDK 21 Alpine image
FROM eclipse-temurin:21-jdk-alpine

# 2️⃣ Set working directory inside the container
WORKDIR /app

# 3️⃣ Copy Maven wrapper and pom.xml first (for caching dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 4️⃣ Copy source code
COPY src src

# 5️⃣ Give execute permission to Maven wrapper
RUN chmod +x mvnw

# 6️⃣ Build the Spring Boot app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# 7️⃣ Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# 8️⃣ Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "target/videocall-0.0.1-SNAPSHOT.jar"]
