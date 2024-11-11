package x.cats

import scala.annotation.targetName

object MonadMaker {
	
	case class IO[A](unsafeRun: () => A) {
		def map[B](f: A => B): IO[B] = 
			IO(() => f(unsafeRun()))
		
		def flatMap[B](f: A => IO[B]): IO[B] =
			IO(() => f(unsafeRun()).unsafeRun())
	}

	object IO {
		@targetName("pure")
		def apply[A](value: => A): IO[A] = 
			IO(() => value)
	}

	def possiblyMonad(): Unit = {
		val aPossiblyMonad = IO(45)
		val f = (x: Int) => IO(x + 1)
		val g = (x: Int) => IO(x * 2)
		val pure = (x: Int) => IO(x)


		// prop 1: left-identity
		val leftIdentity = pure(45).flatMap(f) == f(45)
		// prop 2: right-identity
		val rightIdentity = aPossiblyMonad.flatMap(pure) == aPossiblyMonad
		// prop 3: associativity 
		val associativity = aPossiblyMonad.flatMap(f).flatMap(g) == aPossiblyMonad.flatMap(x => f(x).flatMap(g))

		println(leftIdentity)
		println(rightIdentity)
		println(associativity)
		// false negative

		val leftIdentity_v2 = pure(45).flatMap(f).unsafeRun() == f(45).unsafeRun()
		val rightIdentity_v2 = aPossiblyMonad.flatMap(pure).unsafeRun() == aPossiblyMonad.unsafeRun()
		val associativity_v2 = aPossiblyMonad.flatMap(f).flatMap(g).unsafeRun() == 
			aPossiblyMonad.flatMap(x => f(x).flatMap(g)).unsafeRun()

		println(leftIdentity_v2)	// true
		println(rightIdentity_v2)	// true
		println(associativity_v2)	// true

		val fs = (x: Int) => IO {
			println("incrementing")
			x + 1
		}

		val gs = (x: Int) => IO {
			println("doubling")
			x * 2
		}
		
		val associativity_v3 = aPossiblyMonad.flatMap(fs).flatMap(gs).unsafeRun() == 
				aPossiblyMonad.flatMap(x => fs(x).flatMap(gs)).unsafeRun()
		// incrementing
		// doubling
		// incrementing
		// doubling
	}

	def possiblyMonadStory() = {
		val aPossiblyMonad = IO(45)
		val fs = (x: Int) => IO {
			println("incrementing")
			x + 1
		}

		val gs = (x: Int) => IO {
			println("doubling")
			x * 2
		}

		val associativity_v3 = aPossiblyMonad.flatMap(fs).flatMap(gs).unsafeRun() == 
				aPossiblyMonad.flatMap(x => fs(x).flatMap(gs)).unsafeRun()
	}

	def possiblyMonadExample(): Unit = {
		val aPossiblyMonad = IO {
			println("printing my first possibly monad")
			42
		}

		val anotherPM = IO {
			println("my second PM")
			"Scala"
		}

		val aForComprehension = for {	// computation are DESCRIBED, not EXECUTED
			num <- aPossiblyMonad
			lang <- anotherPM
		} yield s"$num-$lang"

		println(aForComprehension.unsafeRun())	// 42-Scala
	}

	def main(v: Array[String]): Unit = {
		possiblyMonadExample()
	}

}
