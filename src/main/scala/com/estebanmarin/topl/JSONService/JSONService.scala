package com.estebanmarin.topl.JSONService

import  zio.*

case class JSONService()

object JSONService:
  def live = ZLayer.succeed(JSONService())
