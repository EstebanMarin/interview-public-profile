package com.estebanmarin.topl.userInput

import zio.*

case class UserInput()
object UserInput:
  val getInputFromUser: IO[Throwable, (String, String, String)] =
    for
      _ <- Console.printLine("Welcome to Esteban's Topl program")
      from <- Console.readLine("From")
      to <- Console.readLine("To")
      path <- Console.readLine("file path")
    yield (from, to, path)
  
  val live = ZLayer.succeed(UserInput)
