## rabbitmq-spring

This is an example spring-boot application, written in kotlin, using a remote rabbitmq-server to transport rpc-calls.
The flow for an rpc-call is `producer -> rabbitmq -> consumer -> rabbitmq -> producer`.
Because of this flow you can scale this application horizontally, by refactoring producers/consumers into client/server applications,
and then just add more consumers on new machines, when they aren't processing producer requests fast enough.

### prerequisites

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [RabbitMQ-server](https://www.rabbitmq.com/#getstarted) running locally, or on a server
(needs port 5672 exposed for messaging, 15672 for admin console)
* [Docker](https://www.docker.com/) if using the provided Docker configuration for RabbitMQ

### configure

the rabbitmq-server needs to be configured like in `rabbitmq-docker/rabbitmq_definitions.json`
(if you are using the docker config, all passwords are the same as usernames).

`src/main/resources/application.yml` holds configuration properties used by spring to interact with the rabbitmq-server
and should be tweaked to fit your rabbitmq-server instance.

### run rabbitmq-docker

##### windows

`gradlew runRabbitmqContainer`

##### unix

`./gradlew runRabbitmqContainer`

### run application

##### windows

`gradlew clean bootRun`

##### unix

`./gradlew clean bootRun`

### example REST call to application

`curl -s "localhost:8080/rpc/call?type=reverse&input=input"`