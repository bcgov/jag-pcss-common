# jag-pcss-common

[![Lifecycle:Stable](https://img.shields.io/badge/Lifecycle-Stable-97ca00)](https://github.com/bcgov/jag-pcss-criminal)
[![Maintainability](https://api.codeclimate.com/v1/badges/a492f352f279a2d1621e/maintainability)](https://codeclimate.com/github/bcgov/jag-pcss-common/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a492f352f279a2d1621e/test_coverage)](https://codeclimate.com/github/bcgov/jag-pcss-common/test_coverage)

PCSS Common WebMethods Replacement

### Recommended Tools
* Intellij
* Docker
* Docker Compose
* Maven
* Java 11
* Lombok

### Application Endpoints

Local Host: http://127.0.0.1:8080

Actuator Endpoint Local: http://localhost:8080/actuator/health

Code Climate: https://codeclimate.com/github/bcgov/jag-pcss-common

WSDL Endpoint Local:
* jag-ccd-application-1-1(SOAP 1.1):
1) localhost:8080/ws/CCD.Source.CCDUserMapping.ws:ccdUserMapping?WSDL
2) localhost:8080/ws/CCD.Source.CivilFileContent.ws:CivilFileContent?WSDL
3) localhost:8080/ws/CCD.Source.CodeValues.ws.provider:CodeValues?WSDL
4) localhost:8080/ws/CCD.Source.CourtLists.ws.provider:CourtList?WSDL
5) localhost:8080/ws/CCD.Source.CriminalFileContent.ws.provider:CriminalFileContent?WSDL
6) localhost:8080/ws/CCD.Source.GetROPReport.ws:GetROPReport?WSDL
7) localhost:8080/ws/CCD.Source.GetUserLogin.WS:getUserLogin?WSDL
8) localhost:8080/ws/CCD.Source.ProcessResults.ws.provider:ProcessResults?WSDL
9) localhost:8080/ws/CCD.Source.GetDocument.ws:GetDocument?WSDL
10) localhost:8080/ws/CCD.Source.DevUtil.ws:DevUtils?WSDL
11) localhost:8080/ws/CCD.Source.GetParticipantInfo.WS:getParticipantInfo?WSDL

* jad-ccd-application-1-2(SOAP 1.2):
1) localhost:8080/ws/CCD.Source.CivilFileContent.ws:CivilFileContentSecure?WSDL
2) localhost:8080/ws/CCD.Source.CodeValues.ws.provider:CodeValuesSecure?WSDL
3) localhost:8080/ws/CCD.Source.CourtLists.ws.provider:CourtListSecure?WSDL
4) localhost:8080/ws/CCD.Source.CriminalFileContent.ws.provider:CriminalFileContentSecure?WSDL
5) localhost:8080/ws/CCD.Source.GetROPReport.ws:GetROPReportSecure?WSDL
6) localhost:8080/ws/CCD.Source.GetDocument.ws:GetDocumentSecure?WSDL

### Required Environmental Variables

BASIC_AUTH_PASS: The password for the basic authentication. This can be any value for local.

BASIC_AUTH_USER: The username for the basic authentication. This can be any value for local.

ORDS_HOST: The url for ords rest package.

ADOBE_HOST: RESTful service url to retrieve justin adobe report

ORACLE_HOST: RESTful service url to retrieve justin report

ORACLE_NAME: name of the server to get justin report

GENERIC_AGENCY_ID: generic agency id being configured on server. The value is part of CodeValuesSecure's request.

GENERIC_PART_ID: generic agency id being configured on server. The value is part of CodeValuesSecure's request.

### Optional Enviromental Variables
SPLUNK_HTTP_URL: The url for the splunk hec.

SPLUNK_TOKEN: The bearer token to authenticate the application.

SPLUNK_INDEX: The index that the application will push logs to. The index must be created in splunk
before they can be pushed to.

### Building the Application
1) Make sure using java 11 for the project modals and sdk
2) Run ```mvn compile```
3) Make sure ```pcss-common-models```, ```pcss-common-secure-models```, and ```pcss-reports-models``` are marked as generated sources roots (xjc)

### Running the application
Option A) Intellij
1) Set env variables.
2) Run the application

Option B) Jar
1) Run ```mvn package```
2) Run ```cd pcss-common-application```
3) Run ```java -jar ./target/pcss-common-application.jar $ENV_VAR$```  (Note that $ENV_VAR$ are environment variables)

Option C) Docker
1) Run ```mvn package```
2) Run ```cd pcss-common-application```
3) Run ```docker build -t pcss-common-application .```
4) Run ```docker run -p 8080:8080 pcss-common-application $ENV_VAR$```  (Note that $ENV_VAR$ are environment variables)

### Pre Commit
1) Do not commit \CRLF use unix line enders
2) Run the linter ```mvn spotless:apply```

### JaCoCo Coverage Report
1) Run ```mvn clean verify```
2) Open ```pcss-code-coverage/target/site/jacoco/index.html``` in a browser
