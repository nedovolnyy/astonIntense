FROM maven:latest AS builder

COPY ../.mvn ./.mvn

COPY ../mvnw .

WORKDIR /config-server

COPY /config-server/pom.xml .

COPY /config-server/src ./src

RUN --mount=type=cache,target=/root/.m2,rw ../mvnw -Pdocker package -DskipTests

FROM openjdk:26-slim

WORKDIR /config-server

COPY --from=builder config-server/target/configserver-*.jar /config-server/config-server.jar

COPY /configs ./configs

EXPOSE 8071

ENTRYPOINT ["java", "-jar", "config-server.jar"]