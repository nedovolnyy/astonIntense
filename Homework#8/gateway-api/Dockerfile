FROM maven:latest AS builder

COPY ../.mvn ./.mvn

COPY ../mvnw .

WORKDIR /gateway-api

COPY /gateway-api/pom.xml .

COPY /gateway-api/src ./src

RUN --mount=type=cache,target=/root/.m2,rw ../mvnw -Pdocker package -DskipTests

FROM openjdk:26-slim

WORKDIR /gateway-api

COPY --from=builder gateway-api/target/gatewayapi-*.jar /gateway-api/gateway-api.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "gateway-api.jar"]