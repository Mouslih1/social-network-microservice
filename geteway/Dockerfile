FROM openjdk:17-oracle
VOLUME /tmp
COPY target/geteway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]