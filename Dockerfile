FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn chạy
FROM openjdk:23-jdk-slim
COPY --from=build /target/mdb-spring-boot-reactive-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]