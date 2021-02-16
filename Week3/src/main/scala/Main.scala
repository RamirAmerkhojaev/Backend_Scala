import scala.math.pow

object Hello extends App {

  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Array[Boolean] = {

    var max = candies(0)
    for (i <- 1 to (candies.length - 1)) {
      if (candies(i) > max)
        max = candies(i)
    }

    candies.foldLeft(Array[Boolean]())((arr, i) => arr :+ ((i + extraCandies) >= max))

  }

  case class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

  def getDecimalValue(head: ListNode): Int = {


      var x = 0
      var cur = head

      while(cur.next!=null){
        x += 1;
        cur = cur.next
      }
      if (head.next == null){
        head.x * pow(2,0).toInt
      }
      else {
        head.x * pow(2,x).toInt+getDecimalValue(head.next)
      }
    }

  def smallerNumbersThanCurrent(nums: Array[Int]): Array[Int] = {

    var res: Array[Int] = for {
      i <- nums
    } yield {
      nums.filter((c: Int) => c < i).length
    }
    res
  }

  def repeatedNTimes(A: Array[Int]): Int = {

    A.foldLeft(Array[Int]())((arr, i) =>
      if (arr.contains(i)) {return i}
      else {
        arr :+ i
      }).head

  }

  def decompressRLElist(nums: Array[Int]): Array[Int] = {

    var arr = Array[Int]()

    for(i <- Range(0, nums.length, 2)){
      arr ++= Array.fill(nums(i))(nums(i+1))
    }

    arr
  }

  def sumZero(n: Int): Array[Int] = {

    var res = Array[Int]()

    for(i <- Range(1, n/2+1)){
      res ++= Array(-i, i)
    }
    if(n % 2 != 0){
      res :+= 0
    }

    return res
  }

  def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = {

    var arr = Array.ofDim[Int](mat.size)

    for(col <- Range(0, mat(0).size); row <- Range(0, mat.size)){
      arr(row) += mat(row)(col)
    }

    arr.zipWithIndex.sortBy(_._1).unzip._2.take(k)
  }

  def findSolution(customfunction: CustomFunction, z: Int): List[List[Int]] = {

    var solutions = List[List[Int]]()

    for(x <- Range(1, 1000)){
      for(y <- Range(1, 1000)){
        if(customfunction.f(x,y) == z){
          solutions ++= List(List(x,y))
        }
      }

    }
    solutions
  }

  def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {

    var arr = Set[Int]()

    for(i <- nums2){
      if(nums1 contains i){
        arr += i
      }
    }
    arr.toArray
  }

  def buildArray(target: Array[Int], n: Int): List[String] = {

    var arr = List[String]()

    var input = 1
    var i = 0

    while(i < target.length){
      if(input == target(i)){
        arr :+= "Push"
        i+=1
      }else{
        arr :+= "Push"
        arr :+= "Pop"
      }
      input += 1
    }
    arr

  }

}