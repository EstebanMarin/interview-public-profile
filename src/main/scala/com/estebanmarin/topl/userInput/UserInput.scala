package com.estebanmarin.topl.userInput

import com.estebanmarin.topl.Domain.*
import zio.*

object StringInputOps:
  extension (st: String)
    def toNode: Node = Node(avenue = st.head.toString, street = st.tail.toInt)

import com.estebanmarin.topl.userInput.StringInputOps.*

case class UserInput()
object UserInput:
  val getInputFromUser: IO[Throwable, (Node, Node, String)] =
    for
      _ <- Console.printLine("Esteban's Marin Topl interview")
      from: String <- Console.readLine("Type starting node Node(avenue, street) => (i.e B5): ")
      to: String <- Console.readLine("Type ending node Node(avenue, street) => (i.e D5)    ")
      path <- Console.readLine("file path [DEFAULT => src/resources/sample-data.json] (enter) => ")
    yield (from.toNode, to.toNode, path)

  val live = ZLayer.succeed(UserInput())
