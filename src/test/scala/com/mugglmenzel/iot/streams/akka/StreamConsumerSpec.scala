package com.mugglmenzel.iot.streams.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{FreeSpecLike, Matchers}

/**
  * Created by d046179 on 3/14/16.
  */
class StreamConsumerSpec extends TestKit(ActorSystem("test-akka-system")) with FreeSpecLike with Matchers {

  implicit val materializer = ActorMaterializer()

  "Test the stream consumer" - {
    val source = Source(1 to 10)
    val probe = TestProbe()

    "should filter out odd numbers" in {
      source.via(StreamConsumer.eatOdds).runWith(TestSink.probe)
        .request(10)
        .expectNext(2, 4, 6, 8, 10)
        .expectComplete()
    }

    "should filter out even numbers" in {
      source.via(StreamConsumer.eatEven).runWith(TestSink.probe)
        .request(10)
        .expectNext(1, 3, 5, 7, 9)
          .expectComplete()
    }

  }


}
