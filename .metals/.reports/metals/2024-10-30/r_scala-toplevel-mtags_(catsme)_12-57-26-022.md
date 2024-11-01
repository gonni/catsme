error id: file://<WORKSPACE>/src/main/scala/x/cats/MonoidStudy.scala:[1258..1261) in Input.VirtualFile("file://<WORKSPACE>/src/main/scala/x/cats/MonoidStudy.scala", "package x.cats

object MonoidStudy{
	import cats.Semigroup
	import cats.instances.int._
	import cats.syntax.semigroup._
	val numbers = (1 to 1000).toList
	// |+| is always associative
	val sumLeft = numbers.foldLeft(0)(_ |+| _)
	val sumRight = numbers.foldRight(0)(_ |+| _)

	// define a general API
	// def combineFold[T](list: List[T])(implicit semigroup: Semigroup[T]): T = 
	// 	list.foldLeft()(_ |+| _)

	// MONOIDS
	import cats.Monoid
	val intMonoid = Monoid[Int]
	val combineInt = intMonoid.combine(23, 999)	
	val zero = intMonoid.empty

	import cats.instances.string._
	val emptyString = Monoid[String].empty

	import cats.instances.option._
	val emptyOption = Monoid[Option[Int]].combine(Option(2), Option.empty[Int])	// Some(2)

	import cats.syntax.monad._
	val combinedOptionFancy = Option(5) |+| Option(7)

	// TODO1
	def combineFold[T](list: List[T])(implicit monoid: Monoid[T]): T = 
		list.foldLeft(monoid.empty)(_ |+| _)

	// TODO2
	val phonebooks = List(
		Map(
			"Alice" -> 234,
			"Bob" -> 647
		),
		Map(
			"Charile" -> 111,
			"Daiel" -> 889
		),
		Map(
			"Tina" -> 331
		)
	)

	import cats.instances.map._
	val massivePhoneBook = combineFold(phonebooks)

	// TODO3
	case class ShoppingCart(itme: List[String], total: Double)
	def 

	def main(v: Array[String]) = {
		// println(sumLeft)
		// println(sumRight)
		// println(combinedOptionFancy)
		// println(combineFold(numbers))
		println(massivePhoneBook)
	}

}
")
file://<WORKSPACE>/src/main/scala/x/cats/MonoidStudy.scala
file://<WORKSPACE>/src/main/scala/x/cats/MonoidStudy.scala:57: error: expected identifier; obtained def
	def main(v: Array[String]) = {
 ^
#### Short summary: 

expected identifier; obtained def