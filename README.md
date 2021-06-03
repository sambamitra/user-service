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

## Application logic

The following logic was used to calculate the users living in and within 50 miles of London - 

- Get all users living in London by calling the api `https://dwp-techtest.herokuapp.com/city/London/users`. This returns a list of 6 users living in London (A)
- Get all users by calling the api `https://dwp-techtest.herokuapp.com/users`. This returns a list of 1000 users (B)
- Calculate the distance between each user in list A and each in list B using the [Haversine Formula](https://en.wikipedia.org/wiki/Haversine_formula). Don't calculate the distance if the user in list A is the same user in list B (for performance reasons)
- If the distance calculated above is less than or equal to 50 miles, add the user from list B in a separate list C - this is the list of users living within 50 miles of an user in London
- At the end of processing, add the lists A (users in London) and C (users within 50 miles of London)
- Convert the combined list to a set to remove duplicate users that might have been added. Sort the set by the user id to display users in order of their id.

## Production ready features

The application has the following production ready features - 
- Application health check at `/actuator/health`.
- Application metrics are exposed via Micrometer to Prometheus at `/actuator/prometheus`. These can be visualised in Grafana.
- A basic CI/CD pipeline has been added using circleci. This builds the project, runs tests and deploys to Heroku.
- Sonar has been added in the pipeline to provide feedback on code quality.

