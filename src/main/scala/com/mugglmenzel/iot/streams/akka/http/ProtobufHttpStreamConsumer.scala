package com.mugglmenzel.iot.streams.akka.http

import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import com.mugglmenzel.iot.model.ReadingProtos.ReadingProto
import com.mugglmenzel.iot.model.{ReadingConverter, ReadingProtos}

/**
  * Created by d046179 on 3/29/16.
  */
object ProtobufHttpStreamConsumer extends HttpStreamConsumer with App {

  lazy val dataBytesParser = Flow[ByteString].map(bytes => ReadingProto.parseFrom(bytes.toArray)).map(ReadingConverter.fromProto)

  consume(
    url = "http://localhost:9000/protobuf/source?produce=1000",
    throttle = 0,
    overflowStrategy = OverflowStrategy.dropNew
  )

}
