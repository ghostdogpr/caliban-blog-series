package client

import client.TrainClient._
import sttp.client._
import sttp.client.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio.console.putStrLn
import zio.{ App, Task, ZIO }

object TrainApp extends App {

  case class Train(`type`: String, platform: String, trainNumber: String, time: String, stops: List[String])

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] = {

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

    AsyncHttpClientZioBackend().flatMap { implicit backend =>
      val uri = uri"https://bahnql.herokuapp.com/graphql"
      val task: Task[List[((String, Boolean), (List[Train], List[Train]))]] =
        query.toRequest(uri).send().map(_.body).absolve

      task.tap(res => putStrLn(s"Result: $res"))
    }.foldM(ex => putStrLn(ex.toString).as(1), _ => ZIO.succeed(0))
  }
}
