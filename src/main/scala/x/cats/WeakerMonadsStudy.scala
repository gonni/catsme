package x.cats
import cats.{Applicative, Apply}
import cats.syntax.flatMap


object WeakerMonadsStudy {
	trait MyFlatMap[M[_]] extends Apply[M] {
		def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
		
		// TODO
		def ap[A, B](wf: M[A => B])(wa: M[A]): M[B] = 
			flatMap(wa)(a => map(wf)(f => f(a)))
	}
	
	trait MyMonad[M[_]] extends Applicative[M] with MyFlatMap[M] {
		// def pure[A](value: A): M[A]
		// def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
		override def map[A, B](ma: M[A])(f: A => B): M[B] = 
			flatMap(ma)(x => pure(f(x)))
	}

	import cats.FlatMap
	import cats.syntax.flatMap._
	import cats.syntax.functor._

	def getPairs[M[_]: FlatMap, A, B](numbers: M[A], chars: M[B]): M[(A, B)] = for {
		n <- numbers
		c <- chars
	} yield (n, c)

	
		
	def main(v: Array[String]) = {

	}
}
