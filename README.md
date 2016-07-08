# SeismicScala
Using Scala as a script language for Seismic Unix


````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

$.suplane.suxwigb.&
````
Is equivalent to

````sh
#!/bin/sh

suplane | suxwigb !
````


````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

($ > "junk.su").suplane.!
($ < "junk.su").suxwigb(title = "Suplane test pattern", label1 = "time (s)", label2 ="trace number").&
````

Is equivalent to

````sh
#!/bin/sh

suplane > junk.su

suxwigb < "junk.su" title='Suplane test pattern' label1='time (s)' label2='trace number' &
````

````scala
#!/bin/sh
exec scala $0 $@
!#

import seismicscala.Command._

($ > "junk.su").suplane.!

val p = $ < "junk.su"
params = Map("title" -> "Suplane test pattern", "label1" -> "time (s)", "label2" -> "trace number")

p.suxwigb(params).&
(p > "suplane.eps").supswigb(params).!
````

Is equivalent to

````sh
#!/bin/sh

suplane > junk.su

suxwigb < 'junk.su' title='Suplane test pattern' label1='time (s)' label2='trace number' 
supswigb < 'junk.su' > 'suplane.eps' title='Suplane test pattern' label1='time (s)' label2='trace number'
````
