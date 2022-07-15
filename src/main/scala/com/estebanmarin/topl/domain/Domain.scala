package com.estebanmarin.topl.domain

import zio.json.*

type Avenue = String
type Street = Int

final case class Measurement(
    startAvenue: Avenue,
    startStreet: Street,
    transitTime: Double,
    endAvenue: Avenue,
    endStreet: Street,
  )
object Measurement:
  given decoder: JsonDecoder[Measurement] = DeriveJsonDecoder.gen[Measurement]

final case class MeasurementTimeStamp(measurementTime: Int, measurements: List[Measurement])
object MeasurementTimeStamp:
  given decoder: JsonDecoder[MeasurementTimeStamp] = DeriveJsonDecoder.gen[MeasurementTimeStamp]

final case class TrafficMeasurements(trafficMeasurements: List[MeasurementTimeStamp])
object TrafficMeasurements:
  given decoder: JsonDecoder[TrafficMeasurements] = DeriveJsonDecoder.gen[TrafficMeasurements]

case class Banana(curvature: Double)
object Banana:
  given decoder: JsonDecoder[Banana] = DeriveJsonDecoder.gen[Banana]
