
# Сборка fat jar
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/chatBot-1.0-SNAPSHOT-shaded.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

