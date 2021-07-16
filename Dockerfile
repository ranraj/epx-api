FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD epx-service/target/epx-service-0.0.1-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'
EXPOSE 8080 8787
ENTRYPOINT ["java","-jar","/app.jar"]