FROM openjdk:11
EXPOSE 8761
ARG JAR_FILE=target/naming-server.jar
COPY ${JAR_FILE} naming-server.jar
ENTRYPOINT [ "java", "-jar", "/naming-server.jar" ]