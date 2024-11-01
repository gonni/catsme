package x.cats

import cats.kernel.Monoid
import cats.Eval

object FoldingStudy {
  
  object ListExcercises {
    def map[A, B](list: List[A])(f : A => B): List[B] = 
        list.foldRight(List.empty[B])((a, currentList) => f(a) :: currentList)
    def flatMap[A, B](list: List[A])(f : A => List[B]): List[B] = 
        list.foldLeft(List.empty[B])((clist, a) => clist.foldRight(f(a))(_ :: _))
    def filter[A](list: List[A])(predicate: A => Boolean): List[A] = 
        list.foldRight(List.empty[A])((a, clist) => if(predicate(a)) a :: clist else clist)
    def combineAll[A](list: List[A])(implicit monoid: Monoid[A]): A = 
        list.foldLeft(monoid.empty)(monoid.combine)
  }

  import cats.Foldable
  import cats.instances.list._
  val sum = Foldable[List].foldLeft(List(1,2,3), 0)(_ + _)    // = 6
  import cats.instances.option._
  val sumOption = Foldable[Option].foldLeft(Option(2), 30)(_ + _)   // = 32

  val sumRight = Foldable[List].foldRight(List(1,2,3), Eval.now(0)) {(num, eval) => 
    eval.map(_ + num)  
  }

  val anotherSum = Foldable[List].combineAll(List(1,2,3))  // implicit Monoid[Int]
  val mappedConcat = Foldable[List].foldMap(List(1,2,3))(_.toString)    // => "123"
  
  // nesting
  import cats.instances.vector._
  val intsNested = List(Vector(1,2,3), Vector(4,5,6))
  (Foldable[List] compose Foldable[Vector]).combineAll(intsNested)

  import cats.syntax.foldable._
  val sum3 = List(1,2,3).combineAll
  val mappedConcat2 = List(1,2,3).foldMap(_.toString)

  def main(v: Array[String]) = {
    import ListExcercises._

    // println(ListExcercises.map((1 to 10).toList)(_ + 1))
    println(sum3)
    println(mappedConcat2)
  }
}
