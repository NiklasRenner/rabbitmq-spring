#!/bin/bash

rabbitmq_name="rabbitmq-rpc"

docker kill $rabitmq_name
docker rm $rabbitmq_name
docker build -t $rabbitmq_name .
docker run -d --name $rabbitmq_name -p 15672:15672 -p 5672:5672 $rabbitmq_name
docker logs -f $rabbitmq_name
