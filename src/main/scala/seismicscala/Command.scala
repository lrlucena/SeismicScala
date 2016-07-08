/*
   _____        _                   _          _____               __      
  / ___/ ___   (_)_____ ____ ___   (_)_____   / ___/ _____ ____ _ / /____ _
  \__ \ / _ \ / // ___// __ `__ \ / // ___/   \__ \ / ___// __ `// // __ `/
 ___/ //  __// /(__  )/ / / / / // // /__    ___/ // /__ / /_/ // // /_/ / 
/____/ \___//_//____//_/ /_/ /_//_/ \___/   /____/ \___/ \__,_//_/ \__,_/ 

*/
package seismicscala

// http://blog.scalac.io/2015/05/21/dynamic-member-lookup-in-scala.html

import scala.language.dynamics
import scala.sys.process.{ stringSeqToProcess, ProcessBuilder }
import scala.util.{ Try, Success, Failure }

case class Command(_in: String = "",
    _out: String = "",
    _append: Boolean = false,
    proc: List[Seq[String]] = Nil) extends Dynamic {

  private[this] def param(key: Any, value: Any): Seq[String] = {
    if (key.toString.isEmpty) {
      value match {
        case a: Product => Seq(a.productIterator.mkString(","))
        case m: Map[_, _] => m.map {
          case (k: String, v) => param(k.toString, v)
          case (k: Symbol, v) => param(k.name, v)
        }.reduce(_ ++ _)
        case a: Symbol => Seq(a.name)
        case _ => Seq(value.toString)
      }
    } else {
      val v = value match {
        case a: Product => a.productIterator.mkString(",")
        case a: Symbol => a.name
        case _ => value.toString
      }
      Seq(s"$key=$v")
    }
  }

  def applyDynamicNamed(name: String)(values: (String, Any)*): Command = {
    val params = values.map { case (a, b) => param(a, b) }.flatten
    copy(proc = { Seq(name) ++ params } :: proc)
  }

  def applyDynamic(name: String)(value: Any): Command = {
    copy(proc = { Seq(name, value.toString) } :: proc)
  }

  def selectDynamic(name: String): Command = {
    copy(proc = Seq(name) :: proc)
  }

  def in(file: String): Command = copy(_in = file)
  
  def <(file: String): Command = in(file)
  
  def out(file: String): Command = copy(_out = file, _append = false)
  
  def >(file: String): Command = out(file)
  
  def append(file: String) = copy(_out = file, _append = true)
  
  def >>(file: String) = append(file)
  
  def exec: String = Try(build.!!) match {
    case Success(v) => v.toString
    case Failure(m) => m.getMessage
  }

  def ! : String = exec
  
  def run: Unit = build.run
  
  def & : Unit = run
  
  def |(command: Command) = {
    val in = if (command._in.nonEmpty) command._in else _in
    val out = if (command._out.nonEmpty) command._out else _out
    val append = if (command._out.nonEmpty) command._append else _append
    copy(proc = command.proc ++ proc, _in = in, _out = out, _append = append)
  }

  override def toString(): String = {
    val p = proc.reverse.map { x => x.mkString(" ") }.mkString(" | ")
    val p_in = if (_in.isEmpty()) p else (p + " < " + _in)
    val p_in_out = if (_out.isEmpty) {
      p_in
    } else {
      if (_append) p_in + " >>" + _out else p_in + " > " + _out
    }
    p_in_out
  }

  private def build(): ProcessBuilder = {
    import scala.sys.process._
    val p = proc.reverse.foldLeft(Process(true))((a, b) => a #| b)
    val p_in = if (_in.isEmpty()) p else (p #< new java.io.File(_in))
    val p_in_out = if (_out.nonEmpty) {
      val file = new java.io.File(_out)
      if (_append) p_in #>> file else p_in #> file
    } else p_in
    p_in_out
  }
}

object Command {
  def apply(): Command = Command("", "")
  val $ = Command("", "")
}
