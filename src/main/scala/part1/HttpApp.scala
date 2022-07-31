package part1

import caliban.Http4sAdapter
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.middleware.CORS
import zio.interop.catz._
import zio._

object HttpApp extends CatsApp {

  override def run: RIO[Scope, Nothing] =
    MyApi.interpreter
      .flatMap(
        interpreter =>
          BlazeServerBuilder[Task]
            .bindHttp(8088, "localhost")
            .withHttpApp(
              Router[Task]("/api/graphql" -> CORS.policy(Http4sAdapter.makeHttpService(interpreter))).orNotFound
            )
            .resource
            .toScopedZIO
      ) *> ZIO.never
}
