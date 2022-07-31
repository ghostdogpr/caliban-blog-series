package client

import client.TrainClient._
import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3._
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._

object TrainApp extends ZIOAppDefault {

  case class Train(`type`: String, platform: String, trainNumber: String, time: String, stops: List[String])

  def run: Task[List[(String, Boolean, (List[Train], List[Train]))]] = {

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

    ZIO
      .serviceWithZIO[SttpBackend[Task, ZioStreams with WebSockets]](_.send(query.toRequest(uri)))
      .map(_.body)
      .absolve
      .tap(res => ZIO.debug(s"Result: $res"))
      .provide(AsyncHttpClientZioBackend.layer())
  }
}
