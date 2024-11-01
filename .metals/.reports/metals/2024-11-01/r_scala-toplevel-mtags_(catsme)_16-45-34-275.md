error id: file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala:[2529..2532) in Input.VirtualFile("file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala", "package x.cats

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.Future
import cats.Monad
import cats.Applicative

object TraverseStudy {
	implicit val ec : ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))
	val servers: List[String] = List("a.com", "b.com", "c.com")
	def getBandwidth(hostname: String): Future[Int] = Future(hostname.length() * 88)

	val allBandwidths: Future[List[Int]] = servers.foldLeft(Future(List.empty[Int])) { (acc, hostname) =>
		val bandFuture: Future[Int] = getBandwidth(hostname)
		for {
			accBandwidths <- acc
			band <- bandFuture
		} yield accBandwidths :+ band
	} 

	val allBandwidthsTraverse: Future[List[Int]] = Future.traverse(servers)(getBandwidth)
	val allBandwidthsSequence: Future[List[Int]] = Future.sequence(servers.map(getBandwidth))

	//TODO 1
	import cats.syntax.applicative._
	import cats.syntax.flatMap._
	import cats.syntax.functor._
	import cats.syntax.apply._
	def listTraverse[F[_]: Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] = 
		list.foldLeft(List.empty[B].pure[F]){ (acc, elem) =>
			val wElem: F[B] = func(elem)
			// for {
			//   acc1 <- acc
			//   el1 <- wElem
			// } yield acc1 :+ el1
			(acc, wElem).mapN(_ :+ _)
		}

	//TODO 2
	def listSequence[F[_]: Applicative, A](list: List[F[A]]): F[List[A]] = 
		listTraverse(list)(c => c)
	
	//TODO 3
	import cats.instances.vector._
	val allpairs = listSequence(List(Vector(1,2), Vector(3,4)))  // Vecotr(List(1,2,3,4))
	val triplePairs = listSequence(List(Vector(1,2), Vector(3,4), Vector(5,6)))
	// listSequence()()
	
	// lession 1/2
	import cats.instances.option._
	def filterAsOption(list: List[Int])(predicate: Int => Boolean): Option[List[Int]] = {
		listTraverse[Option, Int, Int](list)(n => Some(n).filter(predicate))
	}
	
	import cats.data.Validated
	type ErrorsOr[T] = Validated[List[String], T]
	def filterAsValidated(list: List[Int])(predicate: Int => Boolean): ErrorsOr[List[Int]] = {
		listTraverse[ErrorsOr, Int, Int](list) { n=>
			if(predicate(n)) Validated.valid(n)
			else Validated.invalid(List(s"predicate for $n failed"))
		}
	}

	val allTrueValidated = filterAsValidated(List(2,4,6))(_ % 2 == 0)
	val sameFalseValidated = filterAsValidated(List(1,2,3))(_ % 2 == 0)
	// --

	trait MyTraverse[L[_]] {
		def traverse[F[_]: Applicative, A, B](list: L[A])(func: A => F[B]): F[L[B]]
		def sequence[F[_]: Applicative, A](container: L[F[A]]): F[L[A]] =
			traverse(container)(identity)

		type 
		def map[A, B](wa: L[A])(f: A => B): L[B] = 
			traverse(wa)(f)
	}

	import cats.Traverse
	import cats.instances.future._
	val allBandwidthCats = Traverse[List].traverse(servers)(getBandwidth)

	// extension methods
	import cats.syntax.traverse._
	val allBandwidthCats2 = servers.traverse(getBandwidth)

	def main(v: Array[String]) = {
		// println(allpairs)
		// println(triplePairs)

		println(allTrueValidated)
		println(sameFalseValidated)
		
	}
}
")
file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala
file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala:75: error: expected identifier; obtained def
		def map[A, B](wa: L[A])(f: A => B): L[B] = 
  ^
#### Short summary: 

expected identifier; obtained def