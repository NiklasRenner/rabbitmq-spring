## rabbitmq-spring

This is an example spring-boot application, written in kotlin, using a remote rabbitmq-server to transport rpc calls.
The current application doesn't make much send in itself, when it's a server essentially calling itself, 
but you could refactor it into multiple applications, with multiple clients acting as rpc-callers, 
which then contact the rabbitmq-server, which distributes the messages to multiple rpc-consumers, 
which then respond back to the rpc-callers through the rabbitmq-server.

### prerequisites

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [RabbitMq-server](https://www.rabbitmq.com/#getstarted) running locally, or on a server
(needs port 5672 exposed for messaging, 15672 for admin console)

### configure

the rabbitmq-server needs to be configured like in `rabbitmq-docker/rabbitmq_definitions.json`
(if you are using the docker config, all passwords are the same as usernames).

`src/main/resources/application.yml` holds configuration properties used by spring to interact with the rabbitmq-server
and should be tweaked to fit your rabbitmq-server instance.

### run

##### windows

`gradlew clean bootRun`

##### linux

`./gradlew clean bootRun`

