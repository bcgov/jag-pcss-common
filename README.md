# jag-pcss-common

[![Lifecycle:Maturing](https://img.shields.io/badge/Lifecycle-Maturing-007EC6)](https://github.com/bcgov/jag-pcss-criminal)
[![Maintainability](https://api.codeclimate.com/v1/badges/a492f352f279a2d1621e/maintainability)](https://codeclimate.com/github/bcgov/jag-pcss-common/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a492f352f279a2d1621e/test_coverage)](https://codeclimate.com/github/bcgov/jag-pcss-common/test_coverage)

PCSS Common WebMethods Replacement

### Java requirement
The project requires Java 11.

### Running the tests
Since it is not Maven's expected behaviour, it is good to highlight that you have to first run the package phase to 
generate all the required classes and then run the test phase. In code that looks like:
```shell
mvn clean install -DskipTests
mvn test
```
