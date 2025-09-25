FROM eclipse-temurin:17-jre-alpine

RUN apk update && apk add --no-cache libexpat=2.7.2-r0

COPY ./pcss-common-application/target/pcss-common-application.jar pcss-common-application.jar

ENTRYPOINT ["java", "-Xmx1g", "-jar", "/pcss-common-application.jar"]
