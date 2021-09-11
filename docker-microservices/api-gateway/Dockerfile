FROM openjdk:11
EXPOSE 8765
ARG JAR_FILE=target/api-gateway.jar
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT [ "java", "-jar", "/api-gateway.jar" ]