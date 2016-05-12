package com.mugglmenzel.iot.streams.akka.http

import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import com.mugglmenzel.iot.model.ReadingConverter

/**
  * Created by d046179 on 3/29/16.
  */
object JSONHttpStreamConsumer extends HttpStreamConsumer with App {

  lazy val dataBytesParser = Flow[ByteString].map(_.utf8String).map(ReadingConverter.fromJson)

  consume(
    url = "http://localhost:9000/source?produce=100000",
    throttle = 10,
    overflowStrategy = OverflowStrategy.dropNew
  )

}
