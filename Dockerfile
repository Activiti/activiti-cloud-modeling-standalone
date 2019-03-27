#FROM adoptopenjdk/openjdk11:jdk-11.0.2.7-alpine-slim
FROM openjdk:11.0.1-jre-slim-stretch
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar /opt/app.jar
COPY src/main/resources/static /opt/static
COPY docker-entrypoint.sh /
WORKDIR /opt
ENTRYPOINT ["sh", "/docker-entrypoint.sh"]