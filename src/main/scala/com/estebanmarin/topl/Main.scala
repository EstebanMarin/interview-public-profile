package com.estebanmarin
package topl

import com.estebanmarin.topl.algService.*
import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*
import zio.*

import java.io.IOException

val g = EdgeWeightedDigraph()
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

object ZIOApp extends ZIOAppDefault:
//  val program: IO[String, ShortestPathCalc] =
//    ZIO.fromEither(ShortestPath.run2(g, sourceV = 0)).provideLayer(ShortestPath.live)

  def program_v2(source: Int, to: Int) =
    for
      sp: ShortestPathCalc <- ShortestPath.run2(g, source)
      actualPath = sp.pathTo(to).toString
      timeToGet = sp.distToV(to)
      _ <- Console.printLine(s"THIS IS THE PATH ${actualPath.toString} with ===> ${timeToGet}")
    yield ()

  def run = program_v2(0, 6).provideLayer(ShortestPath.live)
