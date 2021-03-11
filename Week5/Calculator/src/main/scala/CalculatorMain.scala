import Main.system.log
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}

import java.security.MessageDigest

object CalculatorMain {

  final case class SayCalculate(name: String)

  def apply(): Behavior[SayCalculate] =
    Behaviors.setup { context =>
      val greeter = context.spawn(Calculator(), "CalculatorStart")
      Behaviors.receiveMessage{ message =>
        val replyTo = context.spawn(CalculatorBot(), message.name)
        greeter ! Calculator.Calculate(message.name, replyTo)
        Behaviors.same
      }
    }
}
