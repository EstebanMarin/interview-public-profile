package com.estebanmarin
package topl

import com.estebanmarin.topl.algService.*
import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*
import com.estebanmarin.topl.userInput.UserInput
import zio.*

object ZIOApp extends ZIOAppDefault:

  val interview  =
    for
      (from, to, path) <- UserInput.getInputFromUser
      _ <- ShortestPath.getPathAndTime(from, to)
    yield ()

  def run = interview.provideLayer(UserInput.live)
