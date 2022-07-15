package com.estebanmarin
package topl

import com.estebanmarin.topl.JSONService.JSONService
import com.estebanmarin.topl.WeightedDiagram.WeightedDiagram
import com.estebanmarin.topl.algService.{ EdgeWeightedGraph, ShortestPath }
import com.estebanmarin.topl.Domain.*
import com.estebanmarin.topl.userInput.UserInput
import zio.*

object ZIOApp extends ZIOAppDefault:
  val interview: ZIO[ShortestPath & JSONService & WeightedDiagram, Throwable, Unit] =
    for
      (from: Node, to: Node, path: String) <- UserInput.getInputFromUser
      trafficMetrics: TrafficMeasurements <- JSONService.JSONFileToClass(path)
      weightedDiagrams: List[EdgeWeightedGraph] <- WeightedDiagram.generateDiagrams(trafficMetrics)
      optimizedPathsRefactor: List[OptimalPerTimeStamp] <- ShortestPath.dijkstraPathAndTimeRefactor(from, to, weightedDiagrams)
//      solution: Solution <- ShortestPath.dijkstraPathAndTime(from, to, weightedDiagrams)
//      test <- weightedDiagrams.map(ShortestPath.dijkstraPathAndTime(from, to, _))
    yield ()

  def run = interview.provide(
    //    ZLayer.Debug.mermaid,
    ShortestPath.live,
    JSONService.live,
    WeightedDiagram.live,
  )
