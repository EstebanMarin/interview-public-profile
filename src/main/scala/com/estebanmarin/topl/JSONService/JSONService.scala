package com.estebanmarin.topl.JSONService

import zio.*

case class JSONService(filePath: String):
  def open() = ZIO.attempt(s"openning connection to")
  def close() = ZIO.attempt(s"openning connection to")

object JSONService:
  def create(filePath: String) = ZIO.attempt(JSONService(filePath))
  def live = ZLayer.fromFunction(create)

//  val cleanConnection = ZIO.acquireReleaseWith(JSONService.create("/resources/sample-data.json"))(_.close())

//  def getFile = ZIO.fromTry()
