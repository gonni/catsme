file://<WORKSPACE>/src/main/scala/x/cats/FoldingStudy.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<WORKSPACE>/.bloop/catsme/bloop-bsp-clients-classes/classes-Metals-rdOJJyOMS-aZ79fhOoqqog== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parser-combinators_3/2.3.0/scala-parser-combinators_3-2.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-core_3/2.12.0/cats-core_3-2.12.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-kernel_3/2.12.0/cats-kernel_3-2.12.0.jar [exists ]
Options:
-Ypartial-unification -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
offset: 1061
uri: file://<WORKSPACE>/src/main/scala/x/cats/FoldingStudy.scala
text:
```scala
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

  val anotherSum = Folable[]@@
  
  def main(v: Array[String]) = {
    import ListExcercises._

    println(ListExcercises.map((1 to 10).toList)(_ + 1))
  }
}

```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2582)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:96)
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