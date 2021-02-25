object Leetcode extends App {
//1
  private def multiply(arr: Array[Int], promotionFunction: (Int, Int) => Int): Int = (promotionFunction(arr(0), arr(1)))

  def maxProduct(nums: Array[Int]): Int =
    multiply(nums.sorted.slice(nums.length-2, nums.length), (x, y) => (x-1) * (y-1))


  //2
  private def takeMaxMin(arr: Array[Int], promotionFunction: (Double, Double) => Double): Double = {
    promotionFunction(arr.sum, arr.length)

  }

  def average(salary: Array[Int]): Double = {
    val newArr = salary.sorted.slice(1, salary.length-1).toArray
    takeMaxMin(newArr, (x,y) =>  x/y)
  }

//3
  case class Day(day: Int, month: Int, year: Int)

    def dayOfTheWeek(day: Int, month: Int, year: Int): String = {

      val days = Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

      val d = Day(year, month-1, day)

      val cal = new java.util.GregorianCalendar(d.day, d.month, d.year)

      days(cal.get(java.util.Calendar.DAY_OF_WEEK) -1)
    }

//4

  def findPairs(nums: Array[Int], k: Int): Int = {


    def cnt(arr: Iterable[Int]): Int = arr.count(_ > 1)


    if(k == 0){
      val newArr = nums.groupBy(identity).map(_._2.length)
      cnt(newArr)
    }
    else {
      val s = nums.toSet
      s.count(n => s.contains((n+k)))
    }
  }



}


