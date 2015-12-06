package com

import akka.actor.ActorRef
import com.Consumer.{ConsumerConf, Event}
import com.rabbitmq.client.Channel
import com.thenewmotion.akka.rabbitmq._
import com.util.JsonSugar
import org.joda.time.DateTime
import org.json4s.native.Serialization.read

import scala.util.{Failure, Success, Try}


object Consumer {
  case class ConsumerConf(exchange: String, queue: String, routingKey: String, durable: Boolean, exclusive: Boolean, autoDelete: Boolean)

  case class Event(eventType: String, createdAt: DateTime, data: Map[String, Any])
}

class Consumer(id: String, connManager: ActorRef, cons: ConsumerConf) extends JsonSugar {

  import cons._
  connManager ! CreateChannel(ChannelActor.props(setupSubscriber), Some(s"subscriber-$id"))

  private def setupSubscriber(channel: Channel, self: ActorRef) {
    channel.queueDeclare(queue, durable, exclusive, autoDelete, null)
    channel.queueBind(queue, exchange, routingKey)
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) {
        val eventAsString = fromBytes(body)
        Try(read[Event](eventAsString)) match {
          case Success(e) => println(s"I should pass this event to another actor to process $e")
          case Failure(t) => println(s"Error parsing the event: $eventAsString")
        }
      }
    }
    channel.basicConsume(queue, true, consumer)
  }

  private def fromBytes(x: Array[Byte]) = new String(x, "UTF-8")
}