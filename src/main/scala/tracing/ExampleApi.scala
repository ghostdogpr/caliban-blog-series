package tracing

import scala.language.postfixOps
import caliban.GraphQL.graphQL
import caliban.schema.GenericSchema
import caliban.{ GraphQL, RootResolver }
import tracing.ExampleData._
import tracing.ExampleService.ExampleService
import zio.URIO
import zio.clock.Clock
import zio.console.Console
import zio.telemetry.opentracing.OpenTracing

object ExampleApi extends GenericSchema[ExampleService with Clock] {

  case class Queries(
    characters: CharactersArgs => URIO[ExampleService with Clock, List[Character]],
    character: CharacterArgs => URIO[ExampleService with Clock, Option[Character]]
  )

  val api: GraphQL[Console with Clock with ExampleService with OpenTracing] =
    graphQL(
      RootResolver(
        Queries(
          args => ExampleService.getCharacters(args.origin),
          args => ExampleService.findCharacter(args.name)
        )
      )
    ) @@ TracingWrapper()

}
