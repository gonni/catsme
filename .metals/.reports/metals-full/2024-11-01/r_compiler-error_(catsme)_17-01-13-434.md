file://<WORKSPACE>/src/main/scala/x/cats/KleisliStudy.scala
### java.lang.AssertionError: assertion failed: MethodType(List(x), List(TypeRef(ThisType(TypeRef(NoPrefix,module class lang)),class String)), TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Option))

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<WORKSPACE>/.bloop/catsme/bloop-bsp-clients-classes/classes-Metals-hMUjSSKNRUuUyJapsEnhtQ== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parser-combinators_3/2.3.0/scala-parser-combinators_3-2.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-core_3/2.12.0/cats-core_3-2.12.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/cats-kernel_3/2.12.0/cats-kernel_3-2.12.0.jar [exists ]
Options:
-Ypartial-unification -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
offset: 87
uri: file://<WORKSPACE>/src/main/scala/x/cats/KleisliStudy.scala
text:
```scala
package x.cats

object KleisliStudy {
  
  val func1: Int = Option[String] => x => if(x@@)
  
  def main(v: Array[String]) = {

  }
}

```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.core.TypeErasure.dotty$tools$dotc$core$TypeErasure$$sigName(TypeErasure.scala:899)
	dotty.tools.dotc.core.TypeErasure.dotty$tools$dotc$core$TypeErasure$$sigName(TypeErasure.scala:900)
	dotty.tools.dotc.core.TypeErasure$.sigName(TypeErasure.scala:204)
	dotty.tools.dotc.core.Signature.$anonfun$2(Signature.scala:111)
	scala.collection.immutable.List.map(List.scala:246)
	dotty.tools.dotc.core.Signature.prependTermParams(Signature.scala:111)
	dotty.tools.dotc.core.Types$MethodOrPoly.computeSignature$2(Types.scala:3735)
	dotty.tools.dotc.core.Types$MethodOrPoly.signature(Types.scala:3752)
	dotty.tools.dotc.core.Denotations$SingleDenotation.signature(Denotations.scala:617)
	dotty.tools.dotc.core.Denotations$SingleDenotation.signature(Denotations.scala:607)
	dotty.tools.dotc.core.Symbols$Symbol.signature(Symbols.scala:195)
	scala.meta.internal.pc.SemanticdbSymbols$.addOverloadIdx$1(SemanticdbSymbols.scala:153)
	scala.meta.internal.pc.SemanticdbSymbols$.addDescriptor$1(SemanticdbSymbols.scala:174)
	scala.meta.internal.pc.SemanticdbSymbols$.addSymName(SemanticdbSymbols.scala:178)
	scala.meta.internal.pc.SemanticdbSymbols$.addOwner$1(SemanticdbSymbols.scala:133)
	scala.meta.internal.pc.SemanticdbSymbols$.addSymName(SemanticdbSymbols.scala:177)
	scala.meta.internal.pc.SemanticdbSymbols$.addOwner$1(SemanticdbSymbols.scala:133)
	scala.meta.internal.pc.SemanticdbSymbols$.addSymName(SemanticdbSymbols.scala:177)
	scala.meta.internal.pc.SemanticdbSymbols$.symbolName(SemanticdbSymbols.scala:116)
	scala.meta.internal.pc.completions.Completions.visit$4(Completions.scala:694)
	scala.meta.internal.pc.completions.Completions.filterInteresting$$anonfun$1(Completions.scala:721)
	scala.collection.immutable.List.foreach(List.scala:333)
	scala.meta.internal.pc.completions.Completions.filterInteresting(Completions.scala:721)
	scala.meta.internal.pc.completions.Completions.completions(Completions.scala:207)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:91)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:155)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: MethodType(List(x), List(TypeRef(ThisType(TypeRef(NoPrefix,module class lang)),class String)), TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Option))