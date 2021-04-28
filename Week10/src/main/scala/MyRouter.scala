import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}

import scala.concurrent.ExecutionContext

trait  Router {
  def route:Route
}

class MyRouter(todoRepository: TodoRepository)(implicit system: ActorSystem[_],  ex:ExecutionContext)
  extends Router
    with  Directives
    with TodoDirectives
    with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route = concat(
    path("ping") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
      }
    },
    pathPrefix("todos") {
      concat(
        get {
          handleWithGeneric(todoRepository.all()) {
            todos => complete(todos)
          }
        },
        post {
          entity(as[CreateTodo]) { createTodo =>
            validateWith(CreateTodoValidator)(createTodo) {
              handle(todoRepository.create(createTodo)) {
              case InMemoryTodoRepository.TodoDuplicateTitle(_) =>
                ApiError.duplicateTitle((createTodo.title))
              case _ =>
                ApiError.generic}
              {
                todo => complete(todo)
              }
            }
          }
        },
        path(Segment) { id: String =>
          concat(
            put {
              entity(as[UpdateTodo]) { updateTodo =>
                validateWith(UpdateTodoValidator)(updateTodo) {
                  handle(todoRepository.update(id, updateTodo)) {
                    case InMemoryTodoRepository.TodoNotFound(_) =>
                      ApiError.todoNotFound(id)
                    case InMemoryTodoRepository.TodoDuplicateTitle(_) =>
                      ApiError.duplicateTitle((updateTodo.title.toString))
                    case _ =>
                      ApiError.generic
                  }
                  {
                    todo => complete(todo)
                  }
                }
              }
            }
          )
        }
      )
    }
  )
}
//todo add update route