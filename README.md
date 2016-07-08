# SeismicScala
Using Scala as a script language for Seismic Unix


````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

$.suplane.suxwigb.&
````


````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

($ > "junk.su").suplane.!
($ < "junk.su").suxwigb(title = "Suplane test pattern", label1 = "time (s)", label2 ="trace number").! 
````


````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

($ > "junk.su").suplane.!

val data = $ < "junk.su"
params = Map("title" -> "Suplane test pattern", "label1" -> "time (s)", "label2" -> "trace number")

data.suxwigb(params).&
(data > "suplane.eps").supswigb(params).!
````
