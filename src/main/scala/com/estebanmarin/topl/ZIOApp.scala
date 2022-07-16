package com.estebanmarin
package topl

import com.estebanmarin.topl.Domain.*
import com.estebanmarin.topl.JSONService.JSONService
import com.estebanmarin.topl.WeightedDiagram.WeightedDiagram
import com.estebanmarin.topl.algService.{ EdgeWeightedGraph, EdgeWeightedGraphNodetrace, ShortestPath }
import com.estebanmarin.topl.userInput.UserInput
import zio.*

object ZIOApp extends ZIOAppDefault:
  val interview: ZIO[ShortestPath & JSONService & WeightedDiagram, Throwable, Unit] =
    for
      (from: Node, to: Node, path: String) <- UserInput.getInputFromUser
      trafficMetrics: TrafficMeasurements <- JSONService.JSONFileToClass(path)
      weightedDiagrams: List[WGraphPerTimeStamp] <- WeightedDiagram.generateDiagrams(trafficMetrics)
      optimizedPaths: OptimalPerTimeStamp <- ShortestPath.dijkstraPathAndTime(from, to, weightedDiagrams)
      jsonSolution: String  <- JSONService.getJsonSolution(optimizedPaths, from, to)
      _  <- ZIO.succeed(println(optimizedPaths))
    yield ()

  def run = interview.provide(
    //    ZLayer.Debug.mermaid,
    ShortestPath.live,
    JSONService.live,
    WeightedDiagram.live,
  )
