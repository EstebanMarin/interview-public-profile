package com.estebanmarin.topl.JSONService

import zio.*

case class JSONService():
  def open() = ZIO.attempt(s"openning connection to")
  def close() = ZIO.succeed(s"openning connection to")

object JSONService:
  def create = ZIO.succeed(JSONService())
  def live = ZLayer.succeed(JSONService())
  // delegating use
  private def getFile(filePath: String) =
    ZIO.acquireRelease(JSONService.create)(_.close())

  def getTransformJSON(filePath: String): ZIO[Any, Throwable, Unit] =
    ZIO.scoped(for
      fib <- getFile(filePath).fork
      _  <- ZIO.sleep(1.second) *> ZIO.succeed("Interrupting") *> fib.interrupt
      _  <- fib.join
    yield ())

//  def getFile = ZIO.fromTry()
