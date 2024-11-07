
FROM maven:3-openjdk-17 AS build
COPY . .
RUN  mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

ENV JAVA_HOME=/usr/local/openjdk-21
WORKDIR /app
COPY target/oscam-0.0.1-SNAPSHOT.jar /app/oscam.jar
EXPOSE 3011
ENTRYPOINT ["java","-jar","oscam.jar"]