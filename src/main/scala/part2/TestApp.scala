package part2

import caliban.CalibanError.ValidationError
import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.console.{ putStrLn, Console }
import zio.{ App, ExitCode, ZIO }

object TestApp extends App {

  val test1: ZIO[Console, ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api1.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- putStrLn(s"Naive Approach - DB Hits: $dbHits")
    } yield 0

  val test2: ZIO[Console, ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api2.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- putStrLn(s"Nested Effects - DB Hits: $dbHits")
    } yield 0

  val test3: ZIO[Console, ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api3.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- putStrLn(s"ZQuery - DB Hits: $dbHits")
    } yield 0

  val test4: ZIO[Console, ValidationError, Int] =
    for {
      dbService   <- DBService()
      resolver    = Api4.resolver(dbService)
      api         = graphQL(RootResolver(resolver))
      interpreter <- api.interpreter
      _           <- interpreter.execute(Query.orders)
      dbHits      <- dbService.hits
      _           <- putStrLn(s"ZQuery with Batch - DB Hits: $dbHits")
    } yield 0

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    (test1 *> test2 *> test3 *> test4 as ExitCode.success)
      .catchAll(error => zio.console.putStrLn(error.toString).as(ExitCode.failure))
}
