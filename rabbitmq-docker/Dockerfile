FROM rabbitmq:3.6.14-management

COPY rabbitmq.config /etc/rabbitmq/
COPY rabbitmq_definitions.json /etc/rabbitmq/

CMD ["rabbitmq-server"]