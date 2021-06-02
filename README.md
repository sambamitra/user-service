[![CircleCI](https://circleci.com/gh/sambamitra/user-service.svg?style=svg)](https://app.circleci.com/pipelines/github/sambamitra/user-service) 
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=bugs)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=code_smells)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=coverage)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=security_rating)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=sqale_index)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=sambamitra_user-service&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=sambamitra_user-service)

# User Service 

This is a super simple service which exposes an API to fetch users living in and within 50 miles of London.

## Technology stack

The following technologies have been used in this project :-

- Framework - Spring Boot
- Reducing boilerplate - Lombok
- API documentation - Swagger

## Build

To build the application, run the following command from the root directory :-

```
mvn clean install
```

This will run unit tests and build the application.

## How to run

### Heroku

The application has been deployed to Heroku and can be accessed at :-

`https://dwp-user-service.herokuapp.com/swagger-ui.html`

### Run in docker

- Run the following commands from the application root directory to bring up the docker container :-

```
docker build -t dwp/userservice:latest .

docker run --name userservice -p 8080:8080 -d dwp/userservice:latest
```

### Run locally

- Run the service either from IntelliJ/Eclipse or terminal as a Spring Boot project

## How to test

Once the application is running :- 

- Go to `http://localhost:8080/actuator/health` to see the health of the application

- Go to `http://localhost:8080/swagger-ui.html` to see the API contract. The API operation(s) can be performed using this

## Distance calculation

The distance between 2 coordinates have been obtained using the [Haversine Formula](https://en.wikipedia.org/wiki/Haversine_formula).

## Production ready features

The application has the following production ready features - 
- Application health check at `/actuator/health`.
- Application metrics are exposed via Micrometer to Prometheus at `/actuator/prometheus`. These can be visualised in Grafana.
- A basic CI/CD pipeline has been added using circleci. This builds the project, runs tests and deploys to Heroku.
- Sonar has been added in the pipeline to provide feedback on code quality.

