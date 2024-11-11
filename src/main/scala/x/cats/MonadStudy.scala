package x.cats

import scala.concurrent.Future
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

object MonadStudy {
  
  /**
    * Pattern
    * - wrapping a value into a M value
    * - the flatMap mechanism
    * 
    * MONAD
    */

  trait MyMonad[M[_]] {
    def pure[A](value: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
    def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => pure(f(a)))
  }

  import cats.Monad
  import cats.instances.option._
  val optionMonad = Monad[Option]
  val anOption = optionMonad.pure(4)  // Option(4)
  val aTransformedOption = optionMonad.flatMap(anOption)(x => if(x % 3 ==0) Some(x + 1) else None )

  import cats.instances.list._
  val listMonad = Monad[List]
  val aList = listMonad.pure(3) // List(3)
  val aTransformedList = listMonad.flatMap(aList)(x => List(x, x + 1))  // List(3,4)

  //TODO 2: use a Monad[Future]
  import cats.instances.future._
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val futureMonad = Monad[Future]
  val aFuture = futureMonad.pure(3)
  val aTransformedFuture = futureMonad.flatMap(aFuture)(x => Future(x + 1))

  // specialized API
  def getPairsList(numbers: List[Int], chars: List[Char]): List[(Int, Char)] = numbers.flatMap(n => chars.map(c => (n, c)))
  def getPairsOption(numbers: Option[Int], chars: Option[Char]): Option[(Int, Char)] = numbers.flatMap(n => chars.map(c => (n, c)))
  // future

  // generalize
  // def getPairs[M[_], A, B](ma: M[A], mb: M[B])(implicit monad: Monad[M]): M[(A, B)] =
  //   monad.flatMap(ma)(a => monad.map(mb)(b => (a, b)))

  // extension methods - weirder import - pure,  flatMap
  import cats.syntax.applicative._  // pure is here
  val oneOption = 1.pure[Option]  // implicit Monad[Option] will be use => Some(1)
  val oneList = 1.pure[List]

  import cats.syntax.flatMap._
  val oneOptionTransformed = oneOption.flatMap(x => (x + 1).pure[Option])

  // TODO 3:
  // Monads extends Fuctors

  val oneOptionMapped = Monad[Option].map(Option(2))(_ + 1)
  import cats.syntax.functor._  // map is here
  val oneOptionMapped2 = oneOption.map(_ + 2)
  val composedOptionFor = for {
    one <- 1.pure[Option]
    two <- 2.pure[Option]
  } yield one + two

  // generalize
  def getPairs[M[_], A, B](ma: M[A], mb: M[B])(implicit monad: Monad[M]): M[(A, B)] =
  monad.flatMap(ma)(a => monad.map(mb)(b => (a, b)))

  // TODO4 : Implement a shorter version of getPairs using for-comprehensions
  def getPairs2[M[_]: Monad, A, B](ma: M[A], mb: M[B]): M[(A, B)] = for {
    a <- ma
    b <- mb
  } yield (a, b)


  def main(v: Array[String]) = {
    println(getPairs(List(1,2,3), List("A","B","C"))) // List((1,A), (1,B), (1,C), (2,A), (2,B), (2,C), (3,A), (3,B), (3,C))
    println(getPairs(Option(1), Option("A"))) // Some((1,A))
  }
}
