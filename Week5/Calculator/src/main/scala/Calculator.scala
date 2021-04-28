import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }

object Calculator {
    final case class Calculate(whom: String, replyTo: ActorRef[Calculated])
    final case class Calculated(whom: String, from: ActorRef[Calculate])

    def apply(): Behavior[Calculate] = Behaviors.receive{ (context, message) =>
      context.log.info("Calculation Start {}", message.whom)

      val router = new MyRouter(message.whom)

      message.replyTo ! Calculated(message.whom, context.self)
      Behaviors.same
    }

}



