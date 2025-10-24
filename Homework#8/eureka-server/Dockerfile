FROM maven:latest AS builder

COPY ../.mvn ./.mvn

COPY ../mvnw .

WORKDIR /eureka-server

COPY /eureka-server/pom.xml .

COPY /eureka-server/src ./src

RUN --mount=type=cache,target=/root/.m2,rw ../mvnw -Pdocker package -DskipTests

FROM openjdk:26-slim

WORKDIR /eureka-server

COPY --from=builder eureka-server/target/eurekaserver-*.jar /eureka-server/eureka-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]