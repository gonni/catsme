file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<WORKSPACE>/.bloop/catsme/bloop-bsp-clients-classes/classes-Metals-hMUjSSKNRUuUyJapsEnhtQ== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parser-combinators_3/2.3.0/scala-parser-combinators_3-2.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-core_3/2.12.0/cats-core_3-2.12.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-kernel_3/2.12.0/cats-kernel_3-2.12.0.jar [exists ]
Options:
-Ypartial-unification -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
offset: 1729
uri: file://<WORKSPACE>/src/main/scala/x/cats/TraverseStudy.scala
text:
```scala
package x.cats

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
	type ErrorsOr[T] = Validated[List[String], @@]
	def filterAsOption(list: List[Int])(predicate: Int => Boolean): Option[List[Int]] = {
		listTraverse[Option, Int, Int](list)(n => Some(n).filter(predicate))
	}
	
	import cats.data.Validated
	def filterAsValidated(list: List[Int])(predicate: Int => Boolean): ErrorsOr[List[Int]]


	def main(v: Array[String]) = {
		println(allpairs)
		println(triplePairs)
	}
}

```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2582)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:94)
	scala.meta.internal.pc.SignatureHelpProvider$.$anonfun$1(SignatureHelpProvider.scala:48)
	scala.collection.StrictOptimizedLinearSeqOps.loop$3(LinearSeq.scala:280)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile(LinearSeq.scala:282)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile$(LinearSeq.scala:278)
	scala.collection.immutable.List.dropWhile(List.scala:79)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:48)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:436)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner