FROM maven:3.6.0-jdk-11-slim AS build

COPY pom.xml /pom.xml
COPY src /src/

RUN mvn clean package -f /pom.xml

FROM openjdk:12

ENV DISCORD_TOKEN=ABC123

COPY --from=build /target/QuestionBot-jar-with-dependencies.jar /QuestionBot.jar

CMD /usr/bin/java -jar /QuestionBot.jar $DISCORD_TOKEN
