FROM openjdk:17-oracle
VOLUME /tmp
COPY target/User-service-0.0.1-SNAPSHOT.jar user.jar
ENTRYPOINT ["java", "-jar", "user.jar"]