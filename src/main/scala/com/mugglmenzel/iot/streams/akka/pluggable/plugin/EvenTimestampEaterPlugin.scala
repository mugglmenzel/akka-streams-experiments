package com.mugglmenzel.iot.streams.akka.pluggable.plugin

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.mugglmenzel.iot.model.Reading
import com.mugglmenzel.iot.streams.akka.pluggable.PluginLike

/**
  * Created by d046179 on 3/16/16.
  */
object EvenTimestampEaterPlugin extends PluginLike {
  override lazy val flow: Flow[Reading, Reading, NotUsed] = Flow[Reading].filter(_.timestamp % 2 != 0)
}
