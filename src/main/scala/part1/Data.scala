package part1

import java.net.URL

object Data {
  sealed trait Color
  object Color {
    case object FAWN  extends Color
    case object BLACK extends Color
    case object OTHER extends Color
  }
  case class Pug(name: String, nicknames: List[String], pictureUrl: Option[URL], color: Color)
  case class PugNotFound(name: String) extends Throwable
}
