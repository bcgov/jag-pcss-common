FROM eclipse-temurin:11-jre-jammy

COPY ./pcss-common-application/target/pcss-common-application.jar pcss-common-application.jar

ENTRYPOINT ["java","-jar","/pcss-common-application.jar"]
