FROM arm64v8/openjdk:19-jdk-slim-buster

COPY target/agro-inteligente-0.0.1-SNAPSHOT.jar /app/agro.jar

WORKDIR /app

CMD ["java", "-jar", "agro.jar"]