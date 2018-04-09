package logstalgia

import fastparse.all.Parsed
import caseapp._

case class Options(input: String, hashIp: Boolean = false)

object LogstaConvert extends CaseApp[Options] with LogLineParser {

  def run(options: Options, arg: RemainingArgs): Unit = {

    val logsSource = {
      val file = new java.io.File(options.input)
      if (file.exists && file.isFile && file.canRead) Some(scala.io.Source.fromFile(file))
      else None
    }

    logsSource.fold[Unit](println("valid access log file name argument is required!")) { buffSrc =>
      val lines = buffSrc.getLines()
      lines.foreach { line =>
        parseLogLine.parse(line) match {
          case Parsed.Failure(_, index, _) => println(s"failed at index: $index")
          case Parsed.Success((timestamp, foo, color, path, remote, status, rt, xForward), _) =>
            val success = if (status.toInt >= 500) "0" else "1"
            val weight = rt * 100
            val ip = {
              val realIP = if (xForward == "N/A") remote else xForward
              if(options.hashIp) IPHasher.hashIp(realIP)
              else realIP
            }
            val p = path.replace('|', '｜')
            println(s"$timestamp|$ip|$p|$status|$weight|$success|$color")
        }
      }
    }
  }
}


