package logstalgia

import java.time._
import fastparse.all._

trait LogLineParser {
  def twice[T](p: Parser[T]) = p ~ p
  def tri[T](p: Parser[T]) = p ~ p ~ p
  def quad[T](p: Parser[T]) = p ~ p ~ p ~ p

  val digit = CharIn('0' to '9')
  val letters = CharsWhileIn('A' to 'Z')
  val notSpace = CharsWhile.raw(_ != ' ').!
  val parseUnixTimestamp = {
    val parseDate: Parser[LocalDate] =
      (quad(digit)  ~ "-" ~
        twice(digit) ~ "-" ~
        twice(digit)).!.map(LocalDate.parse)
    val parseTime = {
      (twice(digit).!.map(_.toInt) ~ NoTrace(":") ~
        twice(digit).!.map(_.toInt) ~ NoTrace(":") ~
        twice(digit).!.map(_.toInt) ~ NoTrace(",") ~
        tri(digit).!.map(_.toInt)).map {
        case (hours,minutes,seconds,millis) =>
          val nanos = millis * 1000000
          LocalTime.of(hours,minutes,seconds,nanos)
      }
    }
    (parseDate ~ NoTrace(" ".rep) ~ parseTime).map {
      case (date,time) =>
        LocalDateTime.of(date,time).toEpochSecond(ZoneOffset.UTC)
    }
  }
  val parseColorByMethod: Parser[String] = ("GET" | "POST" | "PUT" | "DELETE" | letters).!.map{
    case "GET" => "10DD10"
    case "POST" => "1010DD"
    case "PUT" => "1010DD"
    case "DELETE" => "DD1010"
    case _ => "EFEFEF"
  }

  val parseLogLine = parseUnixTimestamp ~ NoTrace(CharsWhile.raw(_ != ']')) ~ AnyChar.rep(exactly = 54).! ~
    parseColorByMethod ~ NoTrace(" ".rep ~ "uri=") ~
    notSpace ~ NoTrace(" ".rep ~ "remote-address=") ~
    notSpace ~ NoTrace(" ".rep ~ "status=") ~
    tri(digit).! ~ NoTrace(" ".rep ~ "process-time=") ~
    digit.rep.!.map(_.toInt) ~ NoTrace(" ".rep ~ "x-forwarded-for=") ~
    notSpace
}
