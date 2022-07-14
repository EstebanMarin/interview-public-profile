package com.estebanmarin.topl.userInput

import zio.*

case class UserInput()
object UserInput:
  val getInputFromUser: IO[Throwable, (Int, Int, String)] =
    for
      _ <- Console.printLine("Esteban's Marin Topl interview")
      from <- Console.readLine("Type starting node (i.e 0) => ")
      to <- Console.readLine("Type ending node (i.e. 6)  =>  ")
      path <- Console.readLine("file path  => ")
    yield (from.toInt, to.toInt, path)

  val live = ZLayer.succeed(UserInput)
