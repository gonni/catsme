package x.basic

object HofsMain {
  def main(v: Array[String]) = {
    val lst = List(1, 2)
    val clist = List("a", "b")

    val product = for {
        l <- lst
        c <- clist
    } yield (l, c)

    println(product) //List((1,a), (1,b), (2,a), (2,b))
  }
}
