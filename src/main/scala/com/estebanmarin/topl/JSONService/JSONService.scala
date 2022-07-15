package com.estebanmarin.topl.JSONService

import zio.*

import java.io.File
import java.util.Scanner

case class JSONService():
  def open() = ZIO.attempt(s"openning connection to")
  def close() = ZIO.succeed(s"openning connection to")

object JSONService:
  def create = ZIO.succeed(JSONService())
  def live = ZLayer.succeed(JSONService())

  private def getFile(filePath: String) =
    ZIO.acquireRelease(JSONService.create)(_.close())

  def openFileScanner(path: String): UIO[Scanner] =
    ZIO.succeed(new Scanner(new File(path)))

  def acquireOpenFile(path: String): IO[Throwable, Unit] =
    Console.printLine(path)

  val testInterruptFileDisplay =
      for
        _ <- acquireOpenFile("src/resources/sample-data.json")
      yield ()


  def getTransformJSON(filePath: String): ZIO[Any, Throwable, Unit] =
    ZIO.scoped(for
      fib <- getFile(filePath).fork
      _  <- ZIO.sleep(1.second) *> ZIO.succeed("Interrupting") *> fib.interrupt
      _  <- fib.join
    yield ())

