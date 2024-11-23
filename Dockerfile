FROM openjdk:21

WORKDIR /app

COPY target/ServerGameManagement-0.0.1-SNAPSHOT.jar ServerGame.jar

CMD ["java", "-jar", "ServerGame.jar"]