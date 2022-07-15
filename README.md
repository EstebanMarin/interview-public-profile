# Esteban Marin Interview process

[Esteban Marin Linked Profile](https://www.linkedin.com/in/estebanmarincom/)

## Description

This is a ZIO 2 app, which stated from a Scala3 seed from [DevInsideYou](https://github.com/DevInsideYou/scala3-seed.g8)
for a sane sbt scaffolding

## Git

Git messages improve through time. I am avoiding rebasing to leave `git log` with timestamps.

### Libraries

```scala
lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
    "org.typelevel" %% "cats-core" % "2.7.0",
    "dev.zio" %% "zio" % "2.0.0",
    "dev.zio" %% "zio-json" % "0.3.0-RC8"
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-16`,
  ).map(_ % Test),
)
```
