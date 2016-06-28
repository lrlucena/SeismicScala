package samples

import seismicscala.Command._

object Dinamico extends App {
  val data = "./"
  val modelfile = s"${data}model.data"
  val datafile = s"${data}cshots.su"

  val param = Map(
    'nangle -> 260, 'fangle -> -65, 'langle -> 65,
    'nt -> 801, 'dt -> 0.004, 'prim -> 1)

  // Generate CS sections

  for (i <- 0 to 225) {
    val fs = i * 0.02
    val sx = 1500 + i * 20
    val fldr = i + 1
    for (j <- 0 to 75) {
      val fg = i * 0.02 + j * 0.02
      val gx = i * 20 + j * 20
      val tracf = j + 1
      val tracl = i * 76 + j + 1

      def triseis(k: Int) = $.triseis(
        xs = (1.5, 6.0), zs = (0, 0), xg = (0.0, 6.0), zg = (0, 0),
        kreflect = k, krecord = 1, fpeak = 15, lscale = 0.5, reftrans = 0,
        ns = 1, fs = fs, ng = 1, fg = fg, param)

      val sushw = $
        .suaddhead(nt = param('nt))
        .sushw(
          key = ("dt", "trid", "scalco"),
          a = (4000, 1, 0))
        .sushw(
          key = ("tracl", "fldr", "tracf", "sx", "gx"),
          a = (tracl, fldr, tracf, sx, gx))
        .sushw(
          key = ("d1", "d2"),
          a = (0.004, 20))

      $.echo(s"sou=$fldr sou_x=$sx rec_x=$gx chan=$tracl").!
      for (k <- 2 to 6) {
        triseis(k) | sushw < modelfile >> s"temp$k" !
      }
    }
    concatenar()
  }

  // concatenando os arquivos:
  def concatenar() = {
    ($ > "tempA").susum("temp2", "temp3").!
    ($ > "tempB").susum("tempA", "temp4").!
    $.rm("-f tempA").!
    ($ > "tempA").susum("tempB", "temp5").!
    ($ > datafile).susum("tempA", "temp6").!
    $.rm("-f temp*").!
  }
}
