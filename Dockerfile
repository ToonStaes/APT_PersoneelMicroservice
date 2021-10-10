#FROM openjdk:8-jdk-alpine
FROM openjdk:18-ea-11-jdk-alpine
ENV MARIADB_DB_HOST "172.0.99.2"
EXPOSE 8051
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
