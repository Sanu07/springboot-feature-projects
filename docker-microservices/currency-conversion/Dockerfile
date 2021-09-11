FROM openjdk:11
EXPOSE 8100
ARG JAR_FILE=target/currency-conversion.jar
COPY ${JAR_FILE} currency-conversion.jar
ENTRYPOINT [ "java", "-jar", "/currency-conversion.jar" ]