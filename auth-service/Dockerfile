FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /auth-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /auth-service
COPY --from=build /auth-service/target/*.jar application.jar
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "application.jar"]