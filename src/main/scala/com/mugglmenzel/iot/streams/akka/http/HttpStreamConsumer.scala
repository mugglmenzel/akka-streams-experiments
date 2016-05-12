package com.mugglmenzel.iot.streams.akka.http

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, Materializer, OverflowStrategy}
import akka.util.ByteString
import com.mugglmenzel.iot.model.Reading

import scala.util.Try

/**
  * Created by d046179 on 3/24/16.
  */
trait HttpStreamConsumer {

  val dataBytesParser: Flow[ByteString, Reading, NotUsed]

  private def throttler(throttle: Int) = Flow[Reading].map { r =>
    Thread.sleep(throttle)
    r
  }

  private lazy val readingExtractor = Flow[Try[HttpResponse]]
    .map(_.map(_.entity.dataBytes.via(dataBytesParser)).getOrElse(Source.empty))
    .flatMapConcat(identity)


  def subscribe(url: String, throttle: Int = 0, buffer: Int = 10, overflowStrategy: OverflowStrategy = OverflowStrategy.backpressure) =
    Source
      .single(HttpRequest(method = HttpMethods.GET, uri = Uri(url)), ())
      .via(Http().superPool[Unit]())
      .map(_._1)
      .via(readingExtractor)
      .via(throttler(throttle))
      .buffer(buffer, overflowStrategy)


  def consume(url: String, throttle: Int = 0, buffer: Int = 10, overflowStrategy: OverflowStrategy = OverflowStrategy.backpressure)(implicit actorSystem: ActorSystem, materializer: Materializer) =
    subscribe(url, throttle, buffer, overflowStrategy)
      .runWith(Sink.foreach(println))


  implicit val actorSystem = ActorSystem("local")
  implicit val materializer = ActorMaterializer()

}
