package client

import client.TrainClient._
import sttp.client3._
import sttp.client3.asynchttpclient.zio.{ send, AsyncHttpClientZioBackend }
import zio.console.putStrLn
import zio.{ App, ExitCode, ZIO }

object TrainApp extends App {

  case class Train(`type`: String, platform: String, trainNumber: String, time: String, stops: List[String])

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] = {

    val trainInStation =
      (TrainInStation.`type` ~
        TrainInStation.platform ~
        TrainInStation.trainNumber ~
        TrainInStation.time ~
        TrainInStation.stops).mapN(Train)

    val query =
      Query.search(Some("Berlin Ostbahnhof")) {
        Searchable.stations {
          Station.name ~
            Station.hasWiFi ~
            Station.timetable {
              Timetable.nextDepatures {
                trainInStation
              } ~
                Timetable.nextArrivals {
                  trainInStation
                }
            }
        }
      }

    val uri = uri"https://api.deutschebahn.com/free1bahnql/v1/graphql"

    send(query.toRequest(uri))
      .map(_.body)
      .absolve
      .tap(res => ZIO.debug(s"Result: $res"))
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode
  }
}
