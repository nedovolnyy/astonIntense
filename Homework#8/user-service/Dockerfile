FROM maven:latest AS builder

COPY ../.mvn ./.mvn

COPY ../mvnw .

WORKDIR /user-service

COPY /user-service/pom.xml .

COPY /user-service/src ./src

RUN --mount=type=cache,target=/root/.m2,rw ../mvnw -Pdocker package -DskipTests

FROM openjdk:26-slim

WORKDIR /user-service

COPY --from=builder user-service/target/userservice-*.jar /user-service/user-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "user-service.jar"]