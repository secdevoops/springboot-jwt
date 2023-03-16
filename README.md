# spring-boot-jwt


A basic Java 17 project built on top of Spring Boot 3.0.3 framework, using Spring Security for 
authentication and JWT for token-based authorization.


## Compile application

```
mvn compile jib:dockerBuild
```

## Run the services
```
docker-compose -f src/main/resources/docker/docker-compose.yml up
```
Once the app is up and running, you can access the Swagger API documentation by visiting 
http://localhost:8080/swagger-ui/index.html.

In case you want to run just de database, run this command.
```
docker-compose -f src/main/resources/docker/docker-compose.yml up db
```

## Default admin user
```
admin@secdevoops.es / password
```




