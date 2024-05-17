
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/event_mvc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/event_db
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=123t456

ENTRYPOINT ["java", "-jar", "app.jar"]
