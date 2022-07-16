package com.estebanmarin.topl.Domain

import zio.json.*

type Avenue = String
type Street = Int
type TimeStamp = Int
type TransitTime = Double

final case class Node(avenue: Avenue, street: Street)
object Node:
  given encoder: JsonEncoder[Node] = DeriveJsonEncoder.gen[Node]

final case class Measurement(
    startAvenue: Avenue,
    startStreet: Street,
    transitTime: TransitTime,
    endAvenue: Avenue,
    endStreet: Street,
  )
object Measurement:
  given decoder: JsonDecoder[Measurement] = DeriveJsonDecoder.gen[Measurement]

final case class MeasurementTimeStamp(measurementTime: TimeStamp, measurements: List[Measurement])
object MeasurementTimeStamp:
  given decoder: JsonDecoder[MeasurementTimeStamp] = DeriveJsonDecoder.gen[MeasurementTimeStamp]

final case class TrafficMeasurements(trafficMeasurements: List[MeasurementTimeStamp])
object TrafficMeasurements:
  given decoder: JsonDecoder[TrafficMeasurements] = DeriveJsonDecoder.gen[TrafficMeasurements]

case class OptimalPath(measurament: TimeStamp, time: TransitTime)
object OptimalPath:
  given encoder: JsonEncoder[OptimalPath] = DeriveJsonEncoder.gen[OptimalPath]

type OptimalPerTimeStamp = (TimeStamp, TransitTime)
case class Solution(
    starting: Node,
    ending: Node,
    solutions: List[OptimalPerTimeStamp],
  )
object Solution:
  given encoder: JsonEncoder[Solution] = DeriveJsonEncoder.gen[Solution]
  def apply(
      starting: Node,
      ending: Node,
      solution: List[OptimalPerTimeStamp],
    ) =
    new Solution(starting, ending, solution)
