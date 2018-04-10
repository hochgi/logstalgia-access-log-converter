import mill._
import mill.scalalib._

object logconv extends ScalaModule {
  def scalaVersion = "2.12.4"
  def mainClass = Some("hochgi.logstalgia.LogstaConvert")
  def ivyDeps = Agg(
    ivy"com.lihaoyi::fastparse:1.0.0",
    ivy"org.lz4:lz4-java:1.4.1",
    ivy"com.github.alexarchambault::case-app:2.0.0-M2"
  )
}
