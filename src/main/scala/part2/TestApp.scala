package part2

import caliban.CalibanError.ValidationError
import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.{ App, ExitCode, IO, ZIO }

object TestApp extends App {

  val test1: IO[ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api1.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- IO.debug(s"Naive Approach - DB Hits: $dbHits")
    } yield 0

  val test2: IO[ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api2.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- IO.debug(s"Nested Effects - DB Hits: $dbHits")
    } yield 0

  val test3: IO[ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api3.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- IO.debug(s"ZQuery - DB Hits: $dbHits")
    } yield 0

  val test4: IO[ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api4.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- IO.debug(s"ZQuery with Batch - DB Hits: $dbHits")
    } yield 0

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    (test1 *> test2 *> test3 *> test4).exitCode
}
