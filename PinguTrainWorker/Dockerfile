FROM openjdk:11-jre

RUN mkdir -p /usr/src/app
COPY build/libs/PinguTrainWorker.jar /usr/src/app
WORKDIR /usr/src/app
EXPOSE 5006
EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "PinguTrainWorker.jar"]
