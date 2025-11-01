# syntax=docker/dockerfile:1
FROM eclipse-temurin:23-jdk AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

ENV GITHUB_USERNAME=${GITHUB_USERNAME}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

COPY ../../src/main/resources/settings.xml /root/.m2/settings.xml
COPY ../../pom.xml .
RUN mvn dependency:go-offline -B -s /root/.m2/settings.xml

RUN ls -la /root/.m2/ && cat /root/.m2/settings.xml

COPY ../../src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:23-jdk

WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

ENV GITHUB_USERNAME=${GITHUB_USERNAME}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

COPY ../../src/main/resources/settings.xml /root/.m2/settings.xml
RUN ls -la /root/.m2/ && cat /root/.m2/settings.xml

COPY --from=build /app/target/*.jar ./accounts.jar
RUN chmod 755 ./accounts.jar

EXPOSE ${SPRING_PORT}

CMD ["java", "-jar", "accounts.jar"]