FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean install compile package
FROM tomcat:8.0-alpine
WORKDIR /usr/local/tomcat/webapps/
COPY --from=MAVEN_BUILD /build/target/ .
EXPOSE 8080
CMD ["catalina.sh", "run"]
