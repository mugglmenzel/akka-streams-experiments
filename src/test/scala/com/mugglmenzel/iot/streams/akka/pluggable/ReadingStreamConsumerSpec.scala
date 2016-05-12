package com.mugglmenzel.iot.streams.akka.pluggable

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FreeSpecLike}

/**
  * Created by d046179 on 3/16/16.
  */
class ReadingStreamConsumerSpec extends FreeSpecLike with Matchers with MockFactory {

  "Test the pluggable reading stream consumer" - {

    "should call the plugin flow method exactly once" in {
      val pluginStub = stub[PluginLike]
      ReadingStreamConsumer.withPlugin(pluginStub)

      (pluginStub.flow _).verify().once()
    }

  }

}
