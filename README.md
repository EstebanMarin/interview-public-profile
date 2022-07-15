# Esteban Marin Interview process

[Esteban Marin Linked Profile](https://www.linkedin.com/in/estebanmarincom/)

## Running the project

```shell
$ esteban-interview> sbt
$ sbt> run
```

---
_Note_: You can run just `sbt`
To display all available options from the SBT tool

```shell
[info] ╭─────────────────────────────────╮
[info] │     List of defined aliases     │
[info] ├─────────────┬───────────────────┤
[info] │ l | ll | ls │ projects          │
[info] │ cd          │ project           │
[info] │ root        │ cd root           │
[info] │ c           │ compile           │
[info] │ ca          │ compile all       │
[info] │ t           │ test              │
[info] │ r           │ run               │
[info] │ rs          │ reStart           │
[info] │ s           │ reStop            │
[info] │ star        │ thankYouStars     │
[info] │ styleCheck  │ fmt check         │
[info] │ styleFix    │ fmt               │
[info] │ up2date     │ dependencyUpdates │
[info] ╰─────────────┴───────────────────╯
git:main:99e67f1:sbt:topl>

```

## Description

This is a ZIO 2 app, which stated from a Scala3 seed from [DevInsideYou](https://github.com/DevInsideYou/scala3-seed.g8)
for a sane sbt scaffolding

## Dijkstra Algorithm and Implementation

This is a known alg therefore I based my implementation in the following resource

- [Alexey-Novakov](https://medium.com/se-notes-by-alexey-novakov/algorithms-in-scala-dijkstra-shortest-path-78c4291dd8ab)

### Git

This started as a PoC so initial commits messages are hard to read. However, they improve through time. I am avoiding
rebasing to leave `git log` with timestamps.

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
