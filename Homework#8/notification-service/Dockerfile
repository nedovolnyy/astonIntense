FROM maven:latest AS builder

COPY ../.mvn ./.mvn

COPY ../mvnw .

WORKDIR /notification-service

COPY /notification-service/pom.xml .

COPY /notification-service/src ./src

RUN --mount=type=cache,target=/root/.m2,rw ../mvnw -Pdocker package -DskipTests

FROM openjdk:26-slim

WORKDIR /notification-service

COPY --from=builder notification-service/target/notificationservice-*.jar /notification-service/notification-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "notification-service.jar"]