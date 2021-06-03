FROM openjdk:8

MAINTAINER ulyer

RUN echo "Asia/Shanghai" > /etc/timezone

ARG JAR_FILE

VOLUME /tmp

COPY target/${JAR_FILE} app.jar

EXPOSE 8090

ENTRYPOINT java -jar app.jar
