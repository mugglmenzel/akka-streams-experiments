package com.mugglmenzel.iot.model

import com.google.gson.Gson

/**
  * Created by d046179 on 3/24/16.
  */
case class Reading(metricId: String, timestamp: Int) extends Ordered[Reading] {
  override def compare(that: Reading): Int = timestamp.compareTo(that.timestamp)
}

object ReadingConverter {

  def fromJson(reading: String): Reading = new Gson().fromJson(reading, classOf[Reading])

  def toJson(reading: Reading): String = new Gson().toJson(reading)

  def toProto(reading: Reading) = ReadingProtos.ReadingProto
    .newBuilder()
    .setMetricId(reading.metricId)
    .setTimestamp(reading.timestamp)
    .build()

  def fromProto(reading: ReadingProtos.ReadingProto) = Reading(reading.getMetricId, reading.getTimestamp)

}