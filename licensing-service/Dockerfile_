FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="Ruben Quispe <ruben.quispem@gmail.com>"

# The application's jar file
ARG JAR_FILE=target/*.jar

#COPY target/*.jar /app/app.
COPY ${JAR_FILE} app.jar

#execute the application
ENTRYPOINT ["java","-jar","/app.jar"]
