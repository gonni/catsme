package x.basic

object HighCurryMain {
  
  def toCurry(f: (Int, Int) => Int): Int => Int => Int = 
    x => y => f(x, y)

  val superAdder_v2 = toCurry(_ + _)

  def main(v: Array[String]) = {
    println(superAdder_v2(10)(20))
  }
}
