FROM gradle:8.13.0-jdk21-alpine AS builder
WORKDIR /app

COPY . .

RUN gradle wrapper

RUN ./gradlew clean build

FROM openjdk:21-slim
WORKDIR /app

RUN mkdir -p /app/build/libs
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]