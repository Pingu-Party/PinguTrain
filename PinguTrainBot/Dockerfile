FROM openjdk:11-jre

RUN mkdir -p /usr/src/app
COPY build/libs/PinguTrainBot.jar /usr/src/app
WORKDIR /usr/src/app
EXPOSE 5005
EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "PinguTrainBot.jar"]