#FROM  openjdk:11-jdk-alpine
FROM  adoptopenjdk/openjdk11:alpine-jre

COPY target/springboot-films-example-web-0.0.1-SNAPSHOT.jar /springboot-films-example-web-0.0.1-SNAPSHOT.jar
EXPOSE 8080

CMD ["java", "-jar", "springboot-films-example-web-0.0.1-SNAPSHOT.jar"]