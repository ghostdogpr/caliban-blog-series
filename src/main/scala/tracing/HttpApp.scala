package tracing

import scala.concurrent.ExecutionContext
import caliban.Http4sAdapter
import io.jaegertracing.Configuration
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import tracing.ExampleService.ExampleService
import zio._
import zio.interop.catz._
import zio.telemetry.opentracing.OpenTracing

object HttpApp extends App {

  type ExampleTask[A] = RIO[ZEnv with ExampleService with OpenTracing, A]

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    ZIO
      .runtime[ZEnv with ExampleService with OpenTracing]
      .flatMap(
        implicit runtime =>
          for {
            interpreter <- ExampleApi.api.interpreter
            _ <- BlazeServerBuilder[ExampleTask](ExecutionContext.global)
                  .bindHttp(8088, "localhost")
                  .withHttpApp(
                    Router[ExampleTask]("/api/graphql" -> CORS(Http4sAdapter.makeHttpService(interpreter))).orNotFound
                  )
                  .resource
                  .toManaged
                  .useForever
          } yield ()
      )
      .provideCustomLayer(
        ExampleService.make(ExampleData.sampleCharacters) ++
          OpenTracing.live(Configuration.fromEnv("Caliban").getTracer)
      )
      .exitCode
}
