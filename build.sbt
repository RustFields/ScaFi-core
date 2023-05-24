val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ScaFi-core",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % "test",
      libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
  )
