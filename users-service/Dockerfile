FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /users-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /users-service
COPY --from=build /users-service/target/*.jar application.jar
EXPOSE 8091

ENTRYPOINT ["java", "-jar", "application.jar"]