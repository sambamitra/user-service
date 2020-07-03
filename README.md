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

### Run in docker

- Run the following commands from the application root directory to bring up the docker container :-

```
docker build -t dwp/userservice:latest .

docer run --name userservice -p 8080:8080 dwp/userservice:latest -d
```

### Run locally

- Run the service either from IntelliJ/Eclipse or terminal as a Spring Boot project

## How to test

Once the application is running :- 

- Go to `http://localhost:8080/actuator/health` to see the health of the application

- Go to `http://localhost:8080/swagger-ui.html` to see the API contract. The API operation(s) can be performed using this

## Technical debt

- Integration tests
