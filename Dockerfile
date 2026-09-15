FROM eclipse-temurin:17-jre-alpine

#CVE Fixes
RUN apk -U upgrade
RUN apk update && apk add --upgrade --no-cache libexpat libpng gnupg # CVE fixes
RUN apk del --force libpng ttf-dejavu
RUN rm -rf /lib/apk/db/installed/libpng

COPY ./pcss-common-application/target/pcss-common-application.jar pcss-common-application.jar

ENTRYPOINT ["java", "-Xmx1g", "-jar", "/pcss-common-application.jar"]
