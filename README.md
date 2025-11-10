-- How to create dockerfile --
  -Successfully build project (mvn clean install)
  -Locate and verify .jar file exists
  -In project, create new file: Dockerfile and add the following
FROM eclipse-temurin:17-jdk
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
  -Install docker on desktop
  -Create profile and verify email
  -Run docker build -t gameflix-backend .
  -Run docker run -p 8080:8080 gameflix-backend   # adjust port if necessary
