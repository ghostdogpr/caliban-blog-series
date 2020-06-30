package tracing

import scala.language.postfixOps
import tracing.ExampleData._
import zio.clock.Clock
import zio.duration._
import zio._

object ExampleService {

  type ExampleService = Has[Service]

  trait Service {
    def getCharacters(origin: Option[Origin]): UIO[List[Character]]

    def findCharacter(name: String): UIO[Option[Character]]
  }

  def getCharacters(origin: Option[Origin]): URIO[ExampleService with Clock, List[Character]] =
    URIO.accessM[ExampleService](_.get.getCharacters(origin)).delay(200 millis)

  def findCharacter(name: String): URIO[ExampleService with Clock, Option[Character]] =
    URIO.accessM[ExampleService](_.get.findCharacter(name)).delay(200 millis)

  def make(initial: List[Character]): ZLayer[Any, Nothing, ExampleService] = ZLayer.fromEffect {
    for {
      characters <- Ref.make(initial)
    } yield new Service {
      def getCharacters(origin: Option[Origin]): UIO[List[Character]] =
        characters.get.map(_.filter(c => origin.forall(c.origin == _)))

      def findCharacter(name: String): UIO[Option[Character]] = characters.get.map(_.find(c => c.name == name))
    }
  }
}
