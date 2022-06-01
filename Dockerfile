FROM openjdk:16
ARG JAR_FILE=target/miemfinance.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8443