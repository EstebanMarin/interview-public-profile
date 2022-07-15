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

  private def openFileScanner(path: String): UIO[Scanner] =
    ZIO.succeed(new Scanner(new File(path)))

  private def closeFileResource(scanner: Scanner): UIO[Unit] =
    ZIO.succeed(s"[ZIOJSON] closing file") *> ZIO.succeed(scanner.close())

  private def useJSONFile(scanner: Scanner): IO[Throwable, String] =
//    if(scanner.hasN)
    ???
  def acquireOpenFile(path: String): IO[Throwable, String] =
    ZIO.acquireReleaseWith(openFileScanner(path))(closeFileResource)(useJSONFile)

  val testInterruptFileDisplay: ZIO[Any, Nothing, Unit] =
    for
      fiber <- acquireOpenFile("src/resources/sample-data.json").fork
      _ <- ZIO.sleep(2.seconds) *> fiber.interrupt
    yield ()

  def getTransformJSON(filePath: String): ZIO[Any, Throwable, Unit] =
    ZIO.scoped(for
      fib <- getFile(filePath).fork
      _ <- ZIO.sleep(1.second) *> ZIO.succeed("Interrupting") *> fib.interrupt
      _ <- fib.join
    yield ())
