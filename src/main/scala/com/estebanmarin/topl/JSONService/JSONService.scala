package com.estebanmarin.topl.JSONService

import  zio.*

case class JSONService():
  def open() = ZIO.succeed(s"openinig connection to")
  def close() = ZIO.succeed(s"openinig connection to")

object JSONService:
  def live = ZLayer.succeed(JSONService())
//  def getFile = ZIO.fromTry()
