package com.mugglmenzel.iot.streams.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import com.mugglmenzel.iot.model.Reading
import org.scalatest.FreeSpecLike

/**
  * Created by d046179 on 3/14/16.
  */
class ReadingStreamJoinerSpec extends TestKit(ActorSystem("reading-stream-joiner-tests")) with FreeSpecLike {

  implicit val materializer = ActorMaterializer()

  "Test the reading stream joiner" - {
    "should join two reading streams by timestamp" in {
      val r1 = new Reading("a", 1000)
      val r2 = new Reading("b", 3000)
      val r3 = new Reading("a", 2000)
      val r4 = new Reading("b", 4000)

      val source1 = Source(List(r1, r2))
      val source2 = Source(List(r3, r4))
      ReadingStreamJoiner.joinSources(source1, source2).runWith(TestSink.probe)
        .request(4)
        .expectNext(r1, r3, r2, r4)
    }

    "should join two reading streams by timestamp with reversed incoming order" in {
      val r1 = new Reading("a", 4000)
      val r2 = new Reading("b", 2000)
      val r3 = new Reading("a", 3000)
      val r4 = new Reading("b", 1000)

      val source1 = Source(List(r1, r2))
      val source2 = Source(List(r3, r4))
      ReadingStreamJoiner.joinSources(source1, source2).runWith(TestSink.probe)
        .request(4)
        .expectNext(r3, r4, r1, r2)
    }
  }

}
