package x.cats

object KleisliStudy {
  
  val func1: Int => Option[String] = x => if(x %2 == 0) Some(s"$x is even") else None
  val func2: Int => Option[Int] = x => Some(x * 3)

  val plainFunc1: Int => String = x => if (x % 2 == 0) s"$x is even" else "fail"
  val plainFunc2: Int => Int = x => x * 3
  val plainFunc3: Int => String = plainFunc2 andThen plainFunc1

  import cats.data.Kleisli
  import cats.instances.option._
  val func1K = Kleisli(func1)
  val func2K = Kleisli(func2)
  val func3K = func2K andThen func1K

  def main(v: Array[String]) = {
    println(func1(1))
    println(func1(2))
    println(plainFunc3(6))
  }
}
