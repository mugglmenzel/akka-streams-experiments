package com.mugglmenzel.iot.streams.akka.pluggable.plugin

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import com.mugglmenzel.iot.model.Reading
import com.mugglmenzel.iot.streams.akka.pluggable.ReadingStreamConsumer
import org.scalamock.scalatest.MockFactory
import org.scalatest.FreeSpecLike

/**
  * Created by d046179 on 3/16/16.
  */
class PluginsValidationSpec extends TestKit(ActorSystem("test-akka-system")) with FreeSpecLike with MockFactory {

  implicit val materializer = ActorMaterializer()

  "Test that plugins work as expected" - {

    val r1 = new Reading("a", 1001)
    val r2 = new Reading("a", 1002)
    val r3 = new Reading("a", 1003)
    val r4 = new Reading("a", 1004)

    "the odd timestamp eater plugin" - {

      "eats one event with an odd timestamp and return one event with an even timestamp" in {
        val source = Source(List(r1, r2))
        source.via(ReadingStreamConsumer.withPlugin(OddTimestampEaterPlugin)).runWith(TestSink.probe)
          .request(2)
          .expectNext(r2)
          .expectComplete()
      }

      "eats multiple events with odd timestamps and returns multiple events with even timestamps" in {
        val source = Source(List(r1, r2, r3, r4))
        source.via(ReadingStreamConsumer.withPlugin(OddTimestampEaterPlugin)).runWith(TestSink.probe)
          .request(4)
          .expectNext(r2, r4)
          .expectComplete()
      }
    }


    "the even timestamp eater plugin" - {

      "eats one event with an even timestamp and return one event with an odd timestamp" in {
        val source = Source(List(r1, r2))
        source.via(ReadingStreamConsumer.withPlugin(EvenTimestampEaterPlugin)).runWith(TestSink.probe)
          .request(2)
          .expectNext(r1)
          .expectComplete()
      }

      "eats multiple events with even timestamps and returns multiple events with odd timestamps" in {
        val source = Source(List(r1, r2, r3, r4))
        source.via(ReadingStreamConsumer.withPlugin(EvenTimestampEaterPlugin)).runWith(TestSink.probe)
          .request(4)
          .expectNext(r1, r3)
          .expectComplete()
      }
    }
  }

}
