file://<WORKSPACE>/src/main/scala/x/cats/ErrorHandlingStudy.scala
### java.lang.AssertionError: assertion failed

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<WORKSPACE>/.bloop/catsme/bloop-bsp-clients-classes/classes-Metals-rdOJJyOMS-aZ79fhOoqqog== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parser-combinators_3/2.3.0/scala-parser-combinators_3-2.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-core_3/2.12.0/cats-core_3-2.12.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-kernel_3/2.12.0/cats-kernel_3-2.12.0.jar [exists ]
Options:
-Ypartial-unification -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
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
import cats.Applicative

object ErrorHandlingStudy {
  trait MyApplicativeError[M[_], E] extends Applicative[M] {
    def raiseError[A](e: E): M[A]
    def handleErrorWith[A](ma")
  }

  trait MyMonadError[M[_], E] extends Monad[M] {
    
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
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:11)
	dotty.tools.dotc.parsing.Scanners$Scanner.lookahead(Scanners.scala:1083)
	dotty.tools.dotc.parsing.Parsers$Parser.termParamClause$$anonfun$1(Parsers.scala:3328)
	dotty.tools.dotc.parsing.Parsers$Parser.enclosed(Parsers.scala:556)
	dotty.tools.dotc.parsing.Parsers$Parser.inParens(Parsers.scala:558)
	dotty.tools.dotc.parsing.Parsers$Parser.termParamClause(Parsers.scala:3344)
	dotty.tools.dotc.parsing.Parsers$Parser.recur$6(Parsers.scala:3368)
	dotty.tools.dotc.parsing.Parsers$Parser.termParamClauses(Parsers.scala:3376)
	dotty.tools.dotc.parsing.Parsers$Parser.defDefOrDcl(Parsers.scala:3667)
	dotty.tools.dotc.parsing.Parsers$Parser.defOrDcl(Parsers.scala:3555)
	dotty.tools.dotc.parsing.Parsers$Parser.templateStatSeq$$anonfun$1(Parsers.scala:4224)
	dotty.tools.dotc.parsing.Parsers$Parser.checkNoEscapingPlaceholders(Parsers.scala:500)
	dotty.tools.dotc.parsing.Parsers$Parser.templateStatSeq(Parsers.scala:4232)
	dotty.tools.dotc.parsing.Parsers$Parser.$anonfun$39(Parsers.scala:4106)
	dotty.tools.dotc.parsing.Parsers$Parser.enclosed(Parsers.scala:556)
	dotty.tools.dotc.parsing.Parsers$Parser.inBraces(Parsers.scala:559)
	dotty.tools.dotc.parsing.Parsers$Parser.inBracesOrIndented(Parsers.scala:570)
	dotty.tools.dotc.parsing.Parsers$Parser.inDefScopeBraces(Parsers.scala:573)
	dotty.tools.dotc.parsing.Parsers$Parser.templateBody(Parsers.scala:4106)
	dotty.tools.dotc.parsing.Parsers$Parser.templateBodyOpt(Parsers.scala:4099)
	dotty.tools.dotc.parsing.Parsers$Parser.template(Parsers.scala:4076)
	dotty.tools.dotc.parsing.Parsers$Parser.templateOpt(Parsers.scala:4084)
	dotty.tools.dotc.parsing.Parsers$Parser.classDefRest(Parsers.scala:3807)
	dotty.tools.dotc.parsing.Parsers$Parser.classDef(Parsers.scala:3802)
	dotty.tools.dotc.parsing.Parsers$Parser.tmplDef(Parsers.scala:3776)
	dotty.tools.dotc.parsing.Parsers$Parser.defOrDcl(Parsers.scala:3561)
	dotty.tools.dotc.parsing.Parsers$Parser.templateStatSeq$$anonfun$1(Parsers.scala:4224)
	dotty.tools.dotc.parsing.Parsers$Parser.checkNoEscapingPlaceholders(Parsers.scala:500)
	dotty.tools.dotc.parsing.Parsers$Parser.templateStatSeq(Parsers.scala:4232)
	dotty.tools.dotc.parsing.Parsers$Parser.$anonfun$39(Parsers.scala:4106)
	dotty.tools.dotc.parsing.Parsers$Parser.enclosed(Parsers.scala:556)
	dotty.tools.dotc.parsing.Parsers$Parser.inBraces(Parsers.scala:559)
	dotty.tools.dotc.parsing.Parsers$Parser.inBracesOrIndented(Parsers.scala:570)
	dotty.tools.dotc.parsing.Parsers$Parser.inDefScopeBraces(Parsers.scala:573)
	dotty.tools.dotc.parsing.Parsers$Parser.templateBody(Parsers.scala:4106)
	dotty.tools.dotc.parsing.Parsers$Parser.templateBodyOpt(Parsers.scala:4099)
	dotty.tools.dotc.parsing.Parsers$Parser.template(Parsers.scala:4076)
	dotty.tools.dotc.parsing.Parsers$Parser.templateOpt(Parsers.scala:4088)
	dotty.tools.dotc.parsing.Parsers$Parser.objectDef(Parsers.scala:3828)
	dotty.tools.dotc.parsing.Parsers$Parser.tmplDef(Parsers.scala:3782)
	dotty.tools.dotc.parsing.Parsers$Parser.defOrDcl(Parsers.scala:3561)
	dotty.tools.dotc.parsing.Parsers$Parser.topStatSeq(Parsers.scala:4163)
	dotty.tools.dotc.parsing.Parsers$Parser.topstats$1(Parsers.scala:4348)
	dotty.tools.dotc.parsing.Parsers$Parser.topstats$1(Parsers.scala:4342)
	dotty.tools.dotc.parsing.Parsers$Parser.compilationUnit$$anonfun$1(Parsers.scala:4353)
	dotty.tools.dotc.parsing.Parsers$Parser.checkNoEscapingPlaceholders(Parsers.scala:500)
	dotty.tools.dotc.parsing.Parsers$Parser.compilationUnit(Parsers.scala:4358)
	dotty.tools.dotc.parsing.Parsers$Parser.parse(Parsers.scala:181)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$1(ParserPhase.scala:32)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$adapted$1(ParserPhase.scala:39)
	scala.Function0.apply$mcV$sp(Function0.scala:42)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:440)
	dotty.tools.dotc.parsing.Parser.parse(ParserPhase.scala:39)
	dotty.tools.dotc.parsing.Parser.runOn$$anonfun$1(ParserPhase.scala:48)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.parsing.Parser.runOn(ParserPhase.scala:48)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:246)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1321)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:262)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:270)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:279)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:67)
	dotty.tools.dotc.Run.compileUnits(Run.scala:279)
	dotty.tools.dotc.Run.compileSources(Run.scala:194)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:165)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:28)
	scala.meta.internal.pc.SimpleCollector.<init>(PcCollector.scala:373)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzyINIT1(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:90)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:117)
```
#### Short summary: 

java.lang.AssertionError: assertion failed