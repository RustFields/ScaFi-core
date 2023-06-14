ThisBuild / version := "2.2.1"
ThisBuild / organization := "io.github.rustfields"
ThisBuild / scalaVersion := "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ScaFi-core",
    assembly / test := (Test / test).value,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % "test"
  )
