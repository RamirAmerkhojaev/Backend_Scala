import scala.collection.mutable.ArrayBuffer

object Hello extends App {

  def ex1(): Unit = {
    println("Hello, world")
  }

  def ex2(a: Int, b: Int): String = {
    if (a < b) {
      a + " is less than " + b
    } else if (a > b) {
      b + " is less than " + a
    } else {
      a + " is equal to "+ b
    }
  }
  def ex4(): String = {
    var x = 5
    var y = 6

    var ans = if ( x <= y ) "Yes" else "No"

    ans
  }

  def ex3(a: Int, b: Int): Int  = {
    a + b
  }

  def ex5(i: Int): String = {
      i match {
      case 1 => "one"
      case 2 => "two"
      case _ => "not 1 or 2"
    }
  }

  def ex6(x: Any):String = x match {
    case s: String => s + " is a String"
    case i: Int => "Int"
    case f: Float => "Float"
    case l: List[_] => "List"
    case a: Array[_] => "Array"
    case _ => "Unknown"
  }

  def ex7(n: Int): Unit = {
    var i = 0
    while(i <= n) {
      println(i)
      i = i + 1
    }

  }

  def ex8(): List[Int] = {
    val fruits = List("apple", "banana", "lime", "orange")

    val fruitLengths = for {
      f <- fruits
      if f.length > 4
    } yield f.length

    fruitLengths
  }


  //ex1()
  //println(ex2(2, 2))
  //println(ex5(3))
  //println(ex3(10, 10))
  //println(ex4())
  //println(ex6(Array(1,2,3,4)))
  //ex7(5)
  //println(ex8())
}