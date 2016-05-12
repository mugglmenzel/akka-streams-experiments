package com.mugglmenzel.iot.streams.akka

import akka.stream.scaladsl.Flow

/**
  * Created by d046179 on 3/14/16.
  */
object StreamConsumer {

  lazy val eatOdds = Flow[Int].filter(_ % 2 == 0)

  lazy val eatEven = Flow[Int].filter(_ % 2 != 0)

}

