package com.estebanmarin
package topl

import com.estebanmarin.topl.algService.*
import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*
import zio.*

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
  def getPathAndTime(source: Int, to: Int) =
    for
      sp: ShortestPathCalc <- ShortestPath.run2(g, source)
      actualPath = sp.pathTo(to).toString
      timeToGet = sp.distToV(to)
      _ <- Console.printLine(s"THIS IS THE PATH ${actualPath.toString} with ===> ${timeToGet}")
    yield ()

  val getInputFromUser: IO[Throwable, (String, String, String)] =
    for
      _ <- Console.printLine("Welcome to Esteban's Topl program")
      from <- Console.readLine("From")
      to <- Console.readLine("To")
      path <- Console.readLine("file path")
    yield (from, to, path)

  val interview =
    for
      (from, to, path) <- getInputFromUser
      _ <- getPathAndTime(from.toInt, to.toInt)
    yield ()


  def run = interview.provideLayer(ShortestPath.live)
