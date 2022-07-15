package com.estebanmarin.topl.JSONService

import com.estebanmarin.topl.Domain.*
import zio.*
import zio.json.*

import java.io.File
import scala.io.*

case class JSONService()

object JSONService:
  def live = ZLayer.succeed(JSONService())

  def open(path: String) = ZIO.attempt(new File(path))
  def close(file: File) = ZIO.succeed(Source.fromFile(file).close())

  implicit class RichFile(file: File):
    def read(): Iterator[String] = Source.fromFile(file).getLines()

  private def useJSON(file: File): IO[Throwable, TrafficMeasurements] =
    val trafficMeasurements: Either[String, TrafficMeasurements] =
      file.read().reduce(_ + _).fromJson[TrafficMeasurements]
    trafficMeasurements match
      case Right(traffic) => ZIO.succeed(traffic)
      case Left(value) => ZIO.fail(new RuntimeException(s"$value"))

  private def openFileAndTransform(path: String): IO[Throwable, TrafficMeasurements] =
    ZIO.acquireReleaseWith(JSONService.open(path))(close)(useJSON)

  def JSONFileToClass(path: String) =
    ZIO.scoped(for
      _ <- Console.printLine(path)
      fib <- openFileAndTransform("src/resources/sample-data.json").fork
      trafficMeasurements: TrafficMeasurements <- fib.join
    yield trafficMeasurements)
