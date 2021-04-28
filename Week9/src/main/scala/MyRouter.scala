import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}

import scala.concurrent.ExecutionContext

class MyRouter()(implicit system: ActorSystem[_],  ex:ExecutionContext) extends Directives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  val route =
    parameter("evaluate") { input  =>

      val gap = input.filter((char) => char != ' ')
      val res = Week2_Calculator.process(gap)
      get {
        complete {
          "Result: " + res
        }
      }
    }
}