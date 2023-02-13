FROM eclipse-temurin:11-jre-alpine

RUN apk upgrade expat  # Fix for CVE-2022-43680

COPY ./pcss-common-application/target/pcss-common-application.jar pcss-common-application.jar

ENTRYPOINT ["java", "-Xmx1g", "-jar", "/pcss-common-application.jar"]
