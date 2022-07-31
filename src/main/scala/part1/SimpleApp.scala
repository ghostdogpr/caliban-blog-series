package part1

import caliban.CalibanError.ValidationError
import zio.interop.catz._
import zio.{ IO, ZIO }

object SimpleApp extends CatsApp {

  override def run: IO[ValidationError, Unit] =
    for {
      interpreter <- MyApi.interpreter
      result      <- interpreter.execute("""
                                      |{
                                      |  findPug(name: "toto") {
                                      |    name
                                      |    pictureUrl
                                      |  }
                                      |}
                                      |""".stripMargin)
      _           <- ZIO.debug(result.data.toString)
    } yield ()
}
