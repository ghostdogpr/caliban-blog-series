name := "caliban-blog-series"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.github.ghostdogpr"         %% "caliban"                       % "1.4.3",
  "com.github.ghostdogpr"         %% "caliban-http4s"                % "1.4.3",
  "com.github.ghostdogpr"         %% "caliban-client"                % "1.4.3",
  "org.http4s"                    %% "http4s-blaze-server"           % "0.23.12",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.3.18"
)

enablePlugins(CalibanPlugin)
