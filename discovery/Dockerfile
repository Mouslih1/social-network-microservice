FROM openjdk:17-oracle
VOLUME /tmp
COPY target/discovery-0.0.1-SNAPSHOT.jar.original discorvey.jar
ENTRYPOINT ["java", "-jar", "discorvey.jar"]