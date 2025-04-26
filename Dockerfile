FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app


COPY --from=build /app/respy-package/target/respy-package-0.0.1-SNAPSHOT.jar app.jar


COPY respy-package/src/main/resources/application.properties ./application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]
