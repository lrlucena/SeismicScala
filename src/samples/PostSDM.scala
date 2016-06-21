package samples

import seismicscala.SuProcess._
object PostSDM extends App {
  val data = "../act3/"
  val inputdata = "${data}zosection_10.su"
  val inputtvt = "tvttables.bin"
  val output = "zosectionPostSTM_5_10.su"

  val nz = 601
  val dz = 5
  val fz = 0
  val nx = 461
  val dx = 10
  val fx = 0

  val gain1 = $
    .sugain(tpow = 0.9)
    .sushw(key = "ntr", a = nx)
    .sushw(key = "tracer", a = 1, b = 1, c = 1, j = nx)
    .sutaper(tr1 = 5, tr2 = 5)

  gain1 < inputdata > "tmp1" !
}

/*

sukdmig2d < tmp1  ttfile=$inputtvt \
            fzt=$fz nzt=$nz dzt=$dz fxt=$fx nxt=$nx dxt=$dx \
            fs=0 ns=601 ds=10 \
            fzo=$fz nzo=$nz dzo=$dz fxo=$fx nxo=$nx dxo=$dx \
            dxm=10 aperx=2000 angmax=80 \
            off0=0 noff=1 doff=0 \
            jpfile=postsdmInfo.dat > $output

suximage < $output perc=99 hbox=300 wbox=650 xbox=650 ybox=0 \
         label1="Depth (m)" label2="Distance (m)" \
         title='Kirchhoff ZO Depth Migration' &

rm -f tmp1

*/
