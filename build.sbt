lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    cancelable in Global := true,
    name := "logstalgia-access-log-converter",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "fastparse" % "1.0.0",
      "org.lz4" % "lz4-java" % "1.4.1",
      "com.github.alexarchambault" %% "case-app" % "2.0.0-M2"
    )
  )
