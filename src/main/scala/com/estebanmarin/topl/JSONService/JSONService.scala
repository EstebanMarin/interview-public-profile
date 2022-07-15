package com.estebanmarin.topl.JSONService

import zio.*

case class JSONService(filePath: String):
  def open() = ZIO.succeed(s"openning connection to")
  def close() = ZIO.succeed(s"openning connection to")

object JSONService:
  def create(filePath: String) = ZIO.attempt(JSONService(filePath))
  def live = ZLayer.fromFunction(create)

  val cleanConnection = ZIO.acquireRelease(JSONService.create("/resources/sample-data.json"))

//  def getFile = ZIO.fromTry()
