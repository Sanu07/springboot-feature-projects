FROM openjdk:11
EXPOSE 8000
VOLUME /var/lib/mysql
ARG JAR_FILE=target/currency-calculator.jar
COPY ${JAR_FILE} currency-calculator.jar
ENTRYPOINT [ "java", "-jar", "/currency-calculator.jar" ]