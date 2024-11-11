package x.basic

import scala.annotation.tailrec

object HOFsCurrying {
	val aHof: (Int, (Int => Int)) => Int = (x, func) => x + 1
	val anotherHof: Int => (Int => Int) = x => (y => y + 2 * x)

	val superfunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = (x, func) => (y => x + y)

	// exams : map. filter, flatMap

	// more examples
	// f(f(f(f(... f(x)))))
	@tailrec
	def nTimes(f: Int => Int, n: Int, x: Int): Int = 
		if(n <= 0) x
		else nTimes(f, n-1, f(x))

	val plusOne = (x: Int) => x + 1
	val tenThousand = nTimes(plusOne, 10000, 0)

	// @tailrec
	def nTimes_v2(f: Int => Int, n: Int): Int => Int = 
		if(n <= 0) (x: Int) => x
		else (x: Int) => nTimes_v2(f, n-1)(f(x))

	val plusTenThousand = nTimes_v2(plusOne, 100000)
	val tenThousand_v2 = plusTenThousand(0)

	// currying = HOFs returning function instances
	val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
	val add3: Int => Int = superAdder(3)
	val invokeSuperAdder = superAdder(3)(100)

	// curried methods = methods with multiple arg list
	def curriedFormatter(format: String)(x: Double): String = format.format(x)

	val standardFormat: (Double => String) = curriedFormatter("%4.2f")
	val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

	def main(v: Array[String]): Unit = {
		println(tenThousand)
	}
}
