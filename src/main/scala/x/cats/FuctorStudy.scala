package x.cats

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.Await
import scala.util.Success
import scala.util.Failure

object FuctorStudy extends App {
	// Option
	val optionInt = Some(1)
	val f = (x: Int) => x + 1
	println(optionInt.map(f))	// Some(2)
	println(optionInt.map(x => x + 1))	// Some(2)
	println(optionInt.map(_ + 1))  // Some(2)

	// Future
	implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
	val futureIntA = Future {
		Thread.sleep(1000)
		1
	}
	val futureIntB = Future {
		Thread.sleep(2000)
		2
	}
	futureIntA.flatMap(a => futureIntB.map(b => a + b)).onComplete{
		case Success(v) => println("result :" + v)	// result :3
		case Failure(e) => e.printStackTrace()
	}
}
