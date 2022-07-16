package com.estebanmarin.topl.WeightedDiagram

import com.estebanmarin.topl.Domain.*
import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*
import com.estebanmarin.topl.algService.*
import zio.*

case class WeightedDiagram()
object WeightedDiagram:
  def live = ZLayer.succeed(WeightedDiagram())
  private def measurementToNode(measurement: List[Measurement]): EdgeWeightedGraphNodetrace =
    val edgeWeightedGraphNode: EdgeWeightedGraphNodetrace = EdgeWeightedGraphNodetrace()
    measurement.map((measurement: Measurement) =>
      edgeWeightedGraphNode.addEdge(
        DirectedEdgeNode(
          from = Node(measurement.startAvenue, measurement.startStreet),
          to = Node(measurement.endAvenue, measurement.endStreet),
          weight = measurement.transitTime,
        )
      )
    )
    edgeWNodeDiagram

  def generateDiagrams(trafficMeasurements: TrafficMeasurements): IO[Throwable, List[WGraphPerTimeStamp]] =
    ZIO.attempt(
      for
        cityMap: MeasurementTimeStamp <- trafficMeasurements.trafficMeasurements
        listOfDiagramsPerTimeStamp: WGraphPerTimeStamp = WGraphPerTimeStamp(cityMap.measurementTime, measurementToNode(cityMap.measurements))
      yield listOfDiagramsPerTimeStamp
    )

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
