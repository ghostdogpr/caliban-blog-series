name := "caliban-blog-series"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.github.ghostdogpr"         %% "caliban"                       % "1.2.1",
  "com.github.ghostdogpr"         %% "caliban-http4s"                % "1.2.1",
  "com.github.ghostdogpr"         %% "caliban-client"                % "1.2.1",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.3.15"
)

enablePlugins(CalibanPlugin)
