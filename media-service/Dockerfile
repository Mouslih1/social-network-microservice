FROM openjdk:17-oracle
VOLUME /tmp
COPY target/media-service-0.0.1-SNAPSHOT.jar media.jar
ENTRYPOINT ["java", "-jar", "media.jar"]