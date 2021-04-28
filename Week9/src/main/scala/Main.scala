import akka.actor.typed.scaladsl.Behaviors

import org.slf4j.{Logger, LoggerFactory}

import akka.actor.typed.ActorSystem

object Main extends App {

  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val rootBehavior = Behaviors.setup[Nothing] { context =>

    implicit val ec = context.executionContext
    implicit val sys = context.system

    val router = new MyRouter()

    MyServer.startHttpServer(router.route)
    Behaviors.empty
  }
  val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")

}