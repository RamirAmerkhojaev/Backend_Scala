import Main.system.log
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import Week2_Calculator._

object CalculatorBot {

  def apply(): Behavior[Calculator.Calculated] = {
    Behaviors.receive { (context, message) =>
      val result = Week2_Calculator.process(message.whom)
      context.log.info(s"Result: ${result} \n")
      Behaviors.stopped
    }
  }
}

