FROM openjdk:17-oracle
VOLUME /tmp
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]