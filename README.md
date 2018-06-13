# SQE-Demo

#### Dedicated to testing Nasa API: https://api.nasa.gov/api.html#sounds

Contains two modules, just to demonstrate two different testing approaches with different frameworks.

* junit-serenity-solution implemented with JUnit style and could be executed via maven command:
```
mvn -pl junit-serenity-solution clean verify
```
Serenity report will be generated into _SQE-Demo\junit-serenity-solution\target\site\serenity\index.html_

* testng-spring-allure-solution implemented with data-driven testing style and could be executed via maven command:
```
mvn -pl testng-spring-allure-solution clean verify
```
```
mvn -pl testng-spring-allure-solution allure:serve will generate Allure report
```
Allure report will be generated into _SQE-Demo\testng-spring-allure-solution\target\site\allure-maven\index.html_

Both modules contains the same amount of equals tests:

- Test: Request sounds without query params => 403 error (Correct behavior, API key is missing) :white_check_mark:
- Test: Request sounds with filer and api key query params => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with limit and api key query params => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with api key query param => 500 error (Because of Internal Server Error) :x: 
- Test: Request sounds with all available query params => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with negative limit query param => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds incorrect api key query param => 403 error (Correct behavior, API key is invalid) :white_check_mark:
- Test: Request sounds with special symbols in filer query param => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with uppercase symbols in filer query param => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with non existence filer query param => 500 error (Because of Internal Server Error) :x:
- Test: Request sounds with null values in query params => 500 error (Because of Internal Server Error) :x:

###### Nasa url and Nasa api key could be modifyed via maven -Dnasa.url=${url} and -Dnasa.api.key=${api.key}
