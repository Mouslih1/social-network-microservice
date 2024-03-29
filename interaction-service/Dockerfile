FROM openjdk:17-oracle
VOLUME /tmp
COPY target/interaction-service-0.0.1-SNAPSHOT.jar interaction.jar
ENTRYPOINT ["java", "-jar", "interaction.jar"]