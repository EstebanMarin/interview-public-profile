package com.estebanmarin.topl.WeightedDiagram

import com.estebanmarin.topl.algService.{DirectedEdge, EdgeWeightedGraph}
import com.estebanmarin.topl.Domain.{MeasurementTimeStamp, TrafficMeasurements}
import zio.*
import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*

case class WeightedDiagram()
object WeightedDiagram:
  val g = EdgeWeightedGraph()
    .addEdge(DirectedEdge(4, 5, 0.35))
    .addEdge(DirectedEdge(5, 4, 0.35))
    .addEdge(DirectedEdge(4, 7, 0.37))
    .addEdge(DirectedEdge(5, 7, 0.28))
    .addEdge(DirectedEdge(7, 5, 0.28))
    .addEdge(DirectedEdge(5, 1, 0.32))
    .addEdge(DirectedEdge(0, 4, 0.38))
    .addEdge(DirectedEdge(0, 2, 0.26))
    .addEdge(DirectedEdge(7, 3, 0.39))
    .addEdge(DirectedEdge(1, 3, 0.29))
    .addEdge(DirectedEdge(2, 7, 0.34))
    .addEdge(DirectedEdge(6, 2, 0.40))
    .addEdge(DirectedEdge(3, 6, 0.52))
    .addEdge(DirectedEdge(6, 0, 0.58))
    .addEdge(DirectedEdge(6, 4, 0.93))
  def live = ZLayer.succeed(WeightedDiagram())
  def generateDiagrams(trafficMeasurements: TrafficMeasurements): IO[Throwable, List[EdgeWeightedGraph]] =
    for
     cityMap: MeasurementTimeStamp <- trafficMeasurements.trafficMeasurements
//     test = cityMap.measurements.map(measurement => )
    yield ()
    ???
