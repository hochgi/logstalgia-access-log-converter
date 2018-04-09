package logstalgia

import net.jpountz.xxhash.XXHashFactory

object IPHasher {

  val IPRegex = "(\\d{1,3}).(\\d{1,3}).(\\d{1,3}).(\\d{1,3})".r
  val xxhashFactory = XXHashFactory.fastestJavaInstance()

  def hashIp(ip: String): String = ip match {
    case IPRegex(a,b,c,d) =>
      val arr = Array(a.toInt.toByte,
                      b.toInt.toByte,
                      c.toInt.toByte,
                      d.toInt.toByte)
      var hash = xxhashFactory.hash32().hash(arr, 0, 4, 786)
      val i3 = hash & 255
      hash >>= 8
      val i2 = hash & 255
      hash >>= 8
      val i1 = hash & 255
      hash >>= 8
      val i0 = hash & 255
      s"$i0.$i1.$i2.$i3 (xxhashed)"
  }
}
