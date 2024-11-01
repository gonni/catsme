file://<WORKSPACE>/src/main/scala/x/cats/ErrorHandlingStudy.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<WORKSPACE>/.bloop/catsme/bloop-bsp-clients-classes/classes-Metals-rdOJJyOMS-aZ79fhOoqqog== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parser-combinators_3/2.3.0/scala-parser-combinators_3-2.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-core_3/2.12.0/cats-core_3-2.12.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-kernel_3/2.12.0/cats-kernel_3-2.12.0.jar [exists ]
Options:
-Ypartial-unification -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
offset: 308
uri: file://<WORKSPACE>/src/main/scala/x/cats/ErrorHandlingStudy.scala
text:
```scala
package x.cats

import cats.Monad
import scala.util.Try
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContextExecutorService
import scala.concurrent.Future

object ErrorHandlingStudy {
  trait MyApplicativeError[M[_], E] extends Applicative[]@@

  trait MyMonadError[M[_], E] extends Monad[M] {
    def raiseError[A](e: E): M[A]
  }

  import cats.MonadError
  import cats.instances.either._  // implicit MonadError
  type ErrorOr[A] = Either[String, A]
  val monadErrorEither = MonadError[ErrorOr, String]
  val success = monadErrorEither.pure(32) // Either[String, Int] = Right(32)
  val failure = monadErrorEither.raiseError[Int]("something wrong")
  // recover
  val handledError: ErrorOr[Int] = monadErrorEither.handleError(failure) {
    case "Badness" => 44
    case _ => 89
  }
  // recoverWith
  val handleError2: ErrorOr[Int] = monadErrorEither.handleErrorWith(failure) {
    case "Badnexx" => monadErrorEither.pure(44)
    case _ => Left("Something else")
  }
  // filter
  val filteredSuccess = monadErrorEither.ensure(success)("Number too small")(_ > 100)

  // Try and Future
  import cats.instances.try_._  // implicit MonadeError[Try], E = Throwable
  val exception = new RuntimeException("Really bad")
  val pureException: Try[Int] = MonadError[Try, Throwable].raiseError(exception) // Try[Nothing]

  import cats.instances.future._
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  MonadError[Future, Throwable].raiseError(exception) // Future which will complete with a Future(exception)

  // applicatives => ApplicativeError
  import cats.data.Validated
  import cats.instances.list._  // implicit Semigorup[List] => ApplicativeError[ErrorsOr, List[String]]
  type ErrorsOr[T] = Validated[List[String], T]
  import cats.ApplicativeError
  val applErrorVal = ApplicativeError[ErrorsOr, List[String]]
  // pure, raiseError, handleError, handleErrorWith



  def main(v: Array[String]) = {
    println(pureException)
    println()
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