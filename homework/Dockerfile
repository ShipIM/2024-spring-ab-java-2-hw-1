FROM openjdk:17-oracle
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]