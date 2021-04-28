import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.slf4j.{Logger, LoggerFactory}

object Main extends App {

  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val system: ActorSystem[CalculatorMain.SayCalculate] = ActorSystem(CalculatorMain(), "calculator")
  log.info("Welcome the the Calculator Application!\n\n")
  log.info("Enter your expression (enter 'q' to exit): ")

  while (true) {
    val input = scala.io.StdIn.readLine()

    val gap = input.filter((char) => char != ' ')
    if (input == "q") {
      System.exit(0)
    }else if (input == "") ""
    else {
      system ! CalculatorMain.SayCalculate(gap)
    }
  }
}
