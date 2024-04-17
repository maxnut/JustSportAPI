FROM maven:3.9.6-eclipse-temurin-17-alpine AS maven-stage
MAINTAINER gruppoquattro
WORKDIR /justsport

# Build del progetto

COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Deploy

FROM tomcat:10.1.20-jdk17 AS tomcat-stage

COPY --from=maven-stage /justsport/target/JustSportAPI-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

ENTRYPOINT ["catalina.sh", "run"]