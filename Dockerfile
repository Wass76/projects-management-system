#FROM maven:3-openjdk-17 AS build
#COPY . .
#RUN  mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/ProjectsManagementSystem-0.0.1-SNAPSHOT.jar ProjectsManagementSystem.jar
EXPOSE 3011
ENTRYPOINT ["java","-jar","ProjectsManagementSystem.jar"]