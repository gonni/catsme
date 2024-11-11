package x.basic

import scala.util.Try
import scala.util.Success
import scala.util.Failure

object ExceptionHandler extends App {
  // Try
  val result: Int = Try {
    10 / 0
  } match {
    case Success(value) => value
    case Failure(e) => 
      e.printStackTrace()
      -1
  }
  println(result) // -1

  // Option
  def makeInt(s: String): Option[Int] =
    try {
      Some(s.toInt)
    } catch {
      case e: NumberFormatException => None
    }
  
  makeInt("123") match {
    case Some(i) => println("result :" + i) // result :123
    case None => println("Error")
  }
  
  // Either
  def safeDiv(x: Int, y: Int): Either[Exception, Int] =
    try Right(x / y)
    catch { case e: Exception => Left(e) }

  println(safeDiv(10, 0)) // Left(java.lang.ArithmeticException: / by zero)
  println(safeDiv(10, 2)) // Right(5)
  
}
