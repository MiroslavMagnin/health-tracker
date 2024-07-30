FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /analytics-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /analytics-service
COPY --from=build /analytics-service/target/*.jar application.jar
EXPOSE 8093

ENTRYPOINT ["java", "-jar", "application.jar"]