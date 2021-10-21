FROM openjdk:11-jre-slim

COPY ./pcss-common-application/target/pcss-common-application.jar pcss-common-application.jar

ENTRYPOINT ["java","-jar","/pcss-common-application.jar"]
