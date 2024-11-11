package x.basic

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

object StdFuncEverywhere extends App {
  val list = List("abc", "def", "ghi")  
  println(list.map(_.toUpperCase()))  // List(ABC, DEF, GHI)
  println(list.flatMap(identity)) // List(a, b, c, d, e, f, g, h, i)
  println(list zip list) // List(abc, def, ghi, abc, def, ghi)
  println(list.andThen(_ + "1"))  // <function1>
  println(list.fold("")(_ + _)) // abcdefghi

  implicit val ec : ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))
  val futureA = Future(1)
  val futureB = Future(2)
  println(futureA.map(_ + 1)) // Future(<not completed>)
  println(futureA.flatMap(a => Future(a+2)))  // Future(<not completed>)
  println(futureA zip futureB)  // Future(Success((1,2)))
  println(futureA.recoverWith(_ => futureB))  // Future(Success(1))
  println(futureA.andThen{  // Future(<not completed>)
    case _ => 10000
  })

  val some = Some(3)
  println(some.map(_ + 1))  // Some(4)
  println(some.flatMap(a => Some(a + 1))) // Some(4)
  println(some zip some)  // Some((3,3))
  println(some.fold(Option[Int])(_ + 10)) // 13
}
