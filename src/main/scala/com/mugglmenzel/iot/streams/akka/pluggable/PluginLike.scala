package com.mugglmenzel.iot.streams.akka.pluggable

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.mugglmenzel.iot.model.Reading

/**
  * Created by d046179 on 3/16/16.
  */
trait PluginLike {

  def flow: Flow[Reading, Reading, NotUsed]

}
