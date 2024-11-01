package x.cats



object ApplicativeStudy {
	
	// Applicative = Functors + pure + ap
	import cats.Applicative
	import cats.instances.list._

	val listApplicative = Applicative[List]
	val aList =listApplicative.pure(2)

	import cats.syntax.applicative._
	val aSweetList = 2.pure[List]	// List(2)
	val aSweetOption = 2.pure[Option]	// Some(2)

	// Monads extend Applicatives
	// Applicative extend Fuctors
	import cats.data.Validated
	type ErrorOr[T] = Validated[List[String], T]
	val aValidValue: ErrorOr[Int] = Validated.valid(43)	// pure
	val aModifiedValidated: ErrorOr[Int] = aValidValue.map(_ + 1) // map
	val validatedApplicative = Applicative[ErrorOr]

	// ap
	// def ap[W[_], B, T](wf: W[B => T])(wa: W[B]): W[T] = ???
	def productWithApplicatives[W[_], A, B](wa: W[A], wb: W[B])(implicit applicative: Applicative[W]): W[(A, B)] = {
		val functionWrapper: W[B => (A, B)] = applicative.map(wa)(a => (b: B) => (a, b))
		applicative.ap(functionWrapper)(wb)
	}

	// Applicative have this ap function
	// Applicative can implements product from Semigroual
	// => Applicative extends Semigroupal

}
