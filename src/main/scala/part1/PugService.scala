package part1

import java.net.URL
import part1.Data.{ Color, Pug, PugNotFound }
import zio.{ IO, UIO, ZIO }

trait PugService {
  def findPug(name: String): IO[PugNotFound, Pug]                          // GET request
  def randomPugPicture: UIO[String]                                        // GET request
  def addPug(pug: Pug): UIO[Unit]                                          // POST request
  def editPugPicture(name: String, pictureUrl: URL): IO[PugNotFound, Unit] // PUT request
}

object PugService {
  val dummy: PugService = new PugService {
    override def findPug(name: String): IO[PugNotFound, Pug] =
      ZIO.succeed(
        Pug(
          "Patrick",
          List("Pat"),
          Some(new URL("https://m.media-amazon.com/images/I/81tRAIFb9OL._SS500_.jpg")),
          Color.FAWN
        )
      )
    override def randomPugPicture: UIO[String] =
      ZIO.succeed("https://m.media-amazon.com/images/I/81tRAIFb9OL._SS500_.jpg")
    override def addPug(pug: Pug): UIO[Unit]                                          = ZIO.unit
    override def editPugPicture(name: String, pictureUrl: URL): IO[PugNotFound, Unit] = ZIO.fail(PugNotFound(name))
  }
}
