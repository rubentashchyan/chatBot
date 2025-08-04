
# Используем JDK 17
FROM eclipse-temurin:17-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем fat jar в контейнер
COPY target/chatBot-1.0-SNAPSHOT-shaded.jar app.jar

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]

