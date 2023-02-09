#stage 1
#Start with a base image containing Java runtime
#This image is in charge of creating and unpacking the JAR application file.
FROM openjdk:11-slim as build

# Add Maintainer Info
LABEL maintainer="Ruben Quispe <ruben.quispem@gmail.com>"

# The application's jar file
#ARG JAR_FILE=target/*.jar

COPY target/*.jar app.jar
#COPY ${JAR_FILE} app.jar

#unpackage jar file
# Unpacks the app.jar copied previously into the filesystem of the build image
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)


#stage 2
#Same Java runtime
#This new image contains the different layers of a Spring Boot app instead of
#the complete JAR file.
FROM openjdk:11-slim

#Add volume pointing to /tmp
VOLUME /tmp

#Copy unpackaged application to new container Copies the different layers from the first image named build
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#execute the application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.optimagrowth.license.LicenseServiceApplication"]
