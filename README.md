# UberBackEnd

## Objective
Creating REST Spring Boot API backend stack for Uber APP

### Maintainer 
Name: Naresh Agrawal, NUID: 001054600<br/>
Name: Akshay Babaji Phapale, NUID: 001316563

## Technology Stack
* Spring Boot

## Prerequisites
* JAVA(JDK)
* Docker

## Running application locally
```
$ mvn package
$ cd target
$ java -jar uber-uber.jar
```

* Shutdown application locally
```
$ ctrl+c
```

## Build Docker image
```
$ docker build -t <image-name> .
```

## Run application by Docker image
```
$ docker run --rm -p 8080:8080 <image-name>
```
The application should be running and listening for HTTP requests on port 8080 on localhost.
http://localhost:8080/