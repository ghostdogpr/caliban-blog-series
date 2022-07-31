package part1

import caliban.Http4sAdapter
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.middleware.CORS
import zio.interop.catz._
import zio._
import zio.blocking.Blocking
import zio.clock.Clock

object HttpApp extends CatsApp {

  type MyTask[A] = RIO[Clock with Blocking, A]

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    MyApi.interpreter
      .flatMap(
        interpreter =>
          BlazeServerBuilder[MyTask]
            .bindHttp(8088, "localhost")
            .withHttpApp(
              Router[MyTask]("/api/graphql" -> CORS.policy(Http4sAdapter.makeHttpService(interpreter))).orNotFound
            )
            .resource
            .toManagedZIO
            .useForever
      )
      .exitCode
}
