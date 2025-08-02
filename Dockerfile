# Базовый образ с JDK 21
FROM eclipse-temurin:21-jdk

# Создаем рабочую директорию
WORKDIR /app

# Копируем jar-файл в контейнер
COPY target/chatBot-1.0-SNAPSHOT.jar app.jar

# Открываем порт, если нужно (не обязательно для Telegram-бота, можно пропустить)
# EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
