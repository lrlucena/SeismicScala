package samples

object Geom extends App {

  import seismicscala.Command._

  val inputdata = "cshots.su"
  val outputdata = "cshotsGeom.su"
  val param = Map('key2 -> "gx", 'key3 -> "sx", 'a -> 0, 'b -> 1)

  val chw = $
    .suchw(key1 = "offset", c = -1, d = 1, param)
    .suchw(key1 = "cdp", c = 1, d = 2, param)

  println(chw < inputdata > outputdata)

  // val suchw1 = $.suchw(key1 = "offset", c = -1, d = 1, param)
  // val suchw2 = $.suchw(key1 = "cdp", c = 1, d = 2, param)

  // suchw1 | suchw2 < inputdata > outputdata !

  //  val suchw1 = $.suchw(key1 = "offset", key2 = "gx", key3 = "sx", a = 0, b = 1, c = -1, d = 1)
  //  val suchw2 = $.suchw(key1 = "cdp", key2 = "gx", key3 = "sx", a = 0, b = 1, c = 1, d = 2)

}
