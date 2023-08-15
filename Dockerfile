FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app.jar
COPY .env .
ENTRYPOINT ["java", "-jar", "/app.jar"]