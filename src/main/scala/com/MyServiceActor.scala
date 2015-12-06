package com

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.configuration.AppConfiguration
import com.controller.Controller
import com.thenewmotion.akka.rabbitmq.ConnectionActor

class MyServiceActor
  extends Actor
  with ActorLogging
  with Controller
  with AppConfiguration {

  val brokerManager: ActorRef = actorRefFactory.actorOf(ConnectionActor.props(factory), "broker")

  Range(1, 5).map(id => new Consumer(s"CONSUMER-$id", brokerManager, consumerConf))

  def receive = runRoute(rootRoute)

  def actorRefFactory = context

}