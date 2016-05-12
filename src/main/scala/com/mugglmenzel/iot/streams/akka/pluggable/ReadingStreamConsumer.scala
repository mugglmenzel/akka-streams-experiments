package com.mugglmenzel.iot.streams.akka.pluggable

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.mugglmenzel.iot.model.Reading

/**
  * Created by d046179 on 3/16/16.
  */
object ReadingStreamConsumer {

  def withPlugin(plugin: PluginLike): Flow[Reading, Reading, NotUsed] = {
    lazy val default = Flow[Reading]
    Option(plugin).fold(default)(p => Option(p.flow).fold(default)(Flow[Reading].via))
  }

}
