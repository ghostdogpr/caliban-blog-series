package client

import client.TrainClient._
import sttp.client._
import sttp.client.asynchttpclient.zio.{ AsyncHttpClientZioBackend, SttpClient }
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

    val uri = uri"https://bahnql.herokuapp.com/graphql"

    SttpClient
      .send(query.toRequest(uri))
      .map(_.body)
      .absolve
      .tap(res => putStrLn(s"Result: $res"))
      .provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .foldM(ex => putStrLn(ex.toString).as(ExitCode.failure), _ => ZIO.succeed(ExitCode.success))
  }
}
