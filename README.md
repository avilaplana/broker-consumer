broker-consumer
===================
This is the result of a spike to understand how Spray + Akka + Rabbitmq can communicate each other.

Microservice that consumes event from rabbitmq

----------

Dependencies
-------------
```
org.scala-lang:scala-library:2.11.5
org.json4s:json4s-native_2.11:3.3.0
org.json4s:json4s-ext_2.10:3.3.0
joda-time:joda-time:2.9.1
io.spray:spray-can_2.11:1.3.3
io.spray:spray-routing_2.11:1.3.3
com.typesafe.akka:akka-actor_2.11:2.4.1
com.thenewmotion.akka:akka-rabbitmq_2.11:1.2.4
```

Event
-------------
```
{
  "eventType": "voucher",
  "createdAt": "2015-12-06T19:22:49Z"
  "data":{
    "key1":"value1",
    "key2":"value2",
    "key3":{
        "key31":"value31",
        "key32":"value32"
    }
  }
}
```

In scala the the json representation is:
```
case class Event(eventType: String, createdAt: DateTime, data: Map[String, Any])
```
