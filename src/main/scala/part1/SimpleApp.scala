package part1

import zio.{ ExitCode, ZIO }
import zio.interop.catz._

object SimpleApp extends CatsApp {

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, ExitCode] =
    (for {
      interpreter <- MyApi.interpreter
      result      <- interpreter.execute("""
                                      |{
                                      |  findPug(name: "toto") {
                                      |    name
                                      |    pictureUrl
                                      |  }
                                      |}
                                      |""".stripMargin)
      _           <- zio.console.putStrLn(result.data.toString)
    } yield ExitCode.success).catchAll(error => zio.console.putStrLn(error.toString).as(ExitCode.failure))
}
