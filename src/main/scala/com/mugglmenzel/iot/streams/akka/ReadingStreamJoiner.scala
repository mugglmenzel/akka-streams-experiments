package com.mugglmenzel.iot.streams.akka

import akka.NotUsed
import akka.stream.SourceShape
import akka.stream.scaladsl._
import com.mugglmenzel.iot.model.Reading

/**
  * Created by d046179 on 3/14/16.
  */
object ReadingStreamJoiner {

  def joinSources(a: Source[Reading, NotUsed], b: Source[Reading, NotUsed]): Source[Reading, NotUsed] =
    Source.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._
      val merge = builder.add(new MergeSorted[Reading]())

      a ~> merge.in0
      b ~> merge.in1

      SourceShape(merge.out)
    })
}

