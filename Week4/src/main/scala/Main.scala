import Calc._
// Input for Calculator Application
import scala.io.StdIn.readLine

object Main extends App {
  println("Welcome the the Calculator Application!\n\n")

  var b = false
  while (!b) {
    print("Enter your expression (enter 'q' to exit): ")
    val input = readLine()

    if (input == "q") {
      b = true
    } else{
      println(s"Result: ${Calc.process(input)} \n")
    }
  }
}
