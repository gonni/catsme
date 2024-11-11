package x.basic

import scala.annotation.tailrec

object Immutable extends App {
  // immutable List
  val lstA = List(1, 2, 3)
  val lstB = List(6, 7, 8)
  
  val lstA2 = lstA :+ 4
  println(lstA :+ 4)  // List(1, 2, 3, 4)
  val lstC = lstA ++ lstB

  // tailrec - no break
  def createNew(limit: Int, targetList: List[Int]) = {
    @tailrec
    def cutTail(idx: Int, lst: List[Int], acc: List[Int]): List[Int] = {
      if(idx == limit) acc
      else 
        cutTail(idx + 1, lst.tail, acc :+ lst.head)
    }
    
    if(limit < targetList.length) cutTail(0, targetList, List())
    else List()
  }
  println("created -> " + createNew(4, lstC)) // created -> List(1, 2, 3, 6)

  // mutable list
  import scala.collection.mutable
  val mLstA = mutable.ListBuffer(1, 2, 3)
  mLstA.addOne(4) 
  println(mLstA)  // ListBuffer(1, 2, 3, 4)

}
