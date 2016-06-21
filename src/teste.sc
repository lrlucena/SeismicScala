import com.github.lrlucena.su.SUProcess._

object teste {

  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val x = $
    .suplane(npl = 3, nt = 1, taper = false, plane1 = plane())
    .suxwigb(title = "ok")                        //> x  : com.github.lrlucena.su.SUProcess = SUProcess(List(Suxwigb(ok), Suplane(
                                                  //| Some(3),Some(1),None,Some(0),None,None,List(Plane(None,None,None,None)))))
x.seq                                             //> res0: List[Seq[String]] = List(List(suplane, npl=3, nt=1), List())
//  val y = $.suplane(npl = 3,nt=1)
  def f(nt: Int) = $.suplane(plane1=None)         //> f: (nt: Int)com.github.lrlucena.su.SUProcess
 // f(4).seq
 // y.seq

}