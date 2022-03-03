FROM openjdk:8u121-jdk-alpine

ENV JAVA_OPTS="-server -Djava.security.egd=file:/dev/./urandom"

COPY build/libs/*.jar  /app.jar

ENTRYPOINT java -DuseLogstash=${LOGSTASH_ENABLED} -Dspring.profiles.active=${SERVICE_PROFILE} -Denvironment=${SERVICE_PROFILE} ${JAVA_OPTS} -jar /app.jar
