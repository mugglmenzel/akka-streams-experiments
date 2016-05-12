package com.mugglmenzel.iot.streams.akka.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import org.scalatest.{FreeSpecLike, Matchers}

/**
  * Created by d046179 on 3/23/16.
  */
class HttpConsumer extends TestKit(ActorSystem("test-akka-system")) with FreeSpecLike with Matchers {

  implicit val materializer = ActorMaterializer()

  //TODO: use wiremock to simulate source endpoint

  "Test the http stream consumer" - {
    "should consume json http stream" in {
      JSONHttpStreamConsumer.subscribe("http://localhost:9000/source").runWith(TestSink.probe)
    }

    "should consume protobuf http stream" in {
      ProtobufHttpStreamConsumer.subscribe("http://localhost:9000/protobuf/source").runWith(TestSink.probe)
    }
  }

}
