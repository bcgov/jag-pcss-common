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

### Required Environmental Variables

BASIC_AUTH_PASS: The password for the basic authentication. This can be any value for local.

BASIC_AUTH_USER: The username for the basic authentication. This can be any value for local.


### Application Endpoints

LOCAL - WSDL Endpoint Common: 
```
http://localhost:8080/common/JusticePCSSCommon.wsProvider:pcssCommon?WSDL
```

LOCAL - WSDL Endpoint Secure: 
```
http://localhost:8080/common/JusticePCSSCommon.wsProvider:pcssCommonSecure?WSDL
```

LOCAL - WSDL Endpoint Report: 
```
http://localhost:8080/common/JusticePCSSCommon.wsProvider:pcssReport?WSDL
```
