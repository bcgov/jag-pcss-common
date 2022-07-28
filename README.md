# jag-pcss-common
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
