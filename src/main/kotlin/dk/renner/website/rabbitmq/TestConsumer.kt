package dk.renner.website.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.Envelope
import com.rabbitmq.client.ShutdownSignalException

class TestConsumer : Consumer {

    override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
        println("got message: ${String(body)}")
    }

    override fun handleConsumeOk(consumerTag: String?) {}

    override fun handleRecoverOk(consumerTag: String?) = throw UnsupportedOperationException()
    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) = throw UnsupportedOperationException()
    override fun handleCancel(consumerTag: String?) = throw UnsupportedOperationException()
    override fun handleCancelOk(consumerTag: String?) = throw UnsupportedOperationException()

}