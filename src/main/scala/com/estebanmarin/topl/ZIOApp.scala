package com.estebanmarin
package topl

import com.estebanmarin.topl.JSONService.JSONService
import com.estebanmarin.topl.algService.ShortestPath
import com.estebanmarin.topl.userInput.UserInput
import zio.*

object ZIOApp extends ZIOAppDefault:
  val interview: ZIO[ShortestPath & JSONService, Throwable, Unit] =
    for
      (from, to, path) <- UserInput.getInputFromUser
      _ <- JSONService.getTransformJSON(path)
      _ <- ShortestPath.dijkstraPathAndTime(from, to)
    yield ()

  def run = interview.provide(ShortestPath.live, JSONService.live)
