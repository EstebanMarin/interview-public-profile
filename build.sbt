import Dependencies._

ThisBuild / organization := "com.estebanmarin"
ThisBuild / scalaVersion := "3.1.3"

ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation",
    "-explain",
    "-feature",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yexplicit-nulls", // experimental (I've seen it cause issues with circe)
    "-Ykind-projector",
    "-Ysafe-init", // experimental (I've seen it cause issues with circe)
  ) ++ Seq("-rewrite", "-indent") ++ Seq("-source", "future-migration")

lazy val `topl` =
  project
    .in(file("."))
    .settings(name := "topl")
    .settings(commonSettings)
    .settings(dependencies)

lazy val commonSettings = commonScalacOptions ++ Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

val circeVersion = "0.14.1"

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
    "org.typelevel" %% "cats-core" % "2.7.0",
    "dev.zio" %% "zio" % "2.0.0",
    "dev.zio" %% "zio-json" % "0.3.0-RC10",
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-16`,
  ).map(_ % Test),
)
