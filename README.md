# SeismicScala
Using Scala as a script language for Seismic Unix


````scala
import seismicscala.Command._

$.suplane.suxwigb.&
````


````scala
import seismicscala.Command._

($ > "junk.su").suplane.!
($ < "junk.su").suxwigb(title = "Suplane test pattern", label1 = "time (s)", label2 ="trace number").! 
````


````scala
import seismicscala.Command._

($ > "junk.su").suplane.!

val data = $ < "junk.su"
params = Map("title" -> "Suplane test pattern", "label1" -> "time (s)", "label2" -> "trace number")

data.suxwigb(params).&
(data > "suplane.eps").supswigb(params).&
````
