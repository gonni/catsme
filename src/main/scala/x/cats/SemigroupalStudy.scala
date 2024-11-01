package x.cats

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.Future

object SemigroupalStudy {
  
  trait MySemigroupal[F[_]] {
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  }

  import cats.Semigroupal
  import cats.instances.option._
  val optionSemigroupal = Semigroupal[Option]
  val aTupleOption = optionSemigroupal.product(Some(123), Some("a string"))
  val aNoneTupled = optionSemigroupal.product(Some(123), None)

  import cats.instances.future._
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val aTupledFuture = Semigroupal[Future].product(Future("the meaning of life"), Future(42))

  import cats.instances.list._
  val aTupledList = Semigroupal[List].product(List(1,2), List("a", "b"))  //cartitoin product

  // study
  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._

  def productWithMonad[F[_], A, B](fa: F[A], fb: F[B])(implicit monad: Monad[F]): F[(A, B)] = 
    for {
      a <- fa
      b <- fb
    } yield (a, b)
    // monad.flatMap(fa)(a => monad.map(fb)(b => (a, b)))
  
  import cats.data.Validated
  type ErrorsOr[T] = Validated[List[String], T]
  val validatedSemigroupal = Semigroupal[ErrorsOr]

  val invalidsCombination = validatedSemigroupal.product(
    Validated.invalid(List("Something wrong", "Something else wrong")),
    Validated.invalid(List("This can't be right"))
  )
  
  type EitherErrorsOr[T] = Either[List[String], T]
  import cats.instances.either._
  val eitherSemigroupal = Semigroupal[EitherErrorsOr]
  val eitherCombination = eitherSemigroupal.product(
    Left(List("Something wrong", "something else wrong")),
    Left(List("This can't be right"))
  )


  def main(v: Array[String]): Unit = {
    // println(aTupleOption)
    // println(aNoneTupled)
    // println(aTupledFuture)
    // println(aTupledList)
    println(invalidsCombination)
    println("------------------")
    println(eitherCombination)

  }
}
