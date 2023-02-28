# sample-reactive-kafka-producer

## Quick start 

1 - (Optional) Modify the Netty port in the [application.yml](src/main/resources/application.yml) file.

2 - Modify Kafka bootstrap server url and kafka topic in file [application-standalone.yml](src/main/resources/application-standalone.yml)
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    template:
      default-topic: app.local.alert
```
3 - Run the application. 
```shell
mvn spring-boot:run 
```
3 - Make an HTTP call by POST to the endpoint with the necessary information. e.g:
```shell
POST http://localhost:9090/message
Content-Type: application/json

{json}
```
