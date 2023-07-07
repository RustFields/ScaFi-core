ThisBuild / version := "5.0.0"
ThisBuild / organization := "io.github.rustfields"
ThisBuild / scalaVersion := "3.3.0"
ThisBuild / scalacOptions ++= Seq("-feature", "-deprecation")

fork := true

lazy val root = project
  .in(file("."))
  .settings(
    name := "ScaFi-core",
    assembly / test := (Test / test).value,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % "test"
  )
