FROM openjdk:17-jdk-alpine

COPY src/target/api-builder-0.0.1-SNAPSHOT.war api-builder-0.0.1.jar

ENTRYPOINT ["java","-jar","/api-builder-0.0.1.jar"]