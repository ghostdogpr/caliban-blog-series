package part1

import caliban.Http4sAdapter
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import zio.console.putStrLn
import zio.interop.catz._
import zio.interop.catz.implicits._
import zio.{ Task, ZIO }

object HttpApp extends CatsApp {

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    MyApi.interpreter
      .flatMap(
        interpreter =>
          BlazeServerBuilder[Task]
            .bindHttp(8088, "localhost")
            .withHttpApp(
              Router[Task]("/api/graphql" -> CORS(Http4sAdapter.makeHttpService(interpreter))).orNotFound
            )
            .resource
            .toManaged
            .useForever
      )
      .catchAll(err => putStrLn(err.toString))
      .as(1)
}
