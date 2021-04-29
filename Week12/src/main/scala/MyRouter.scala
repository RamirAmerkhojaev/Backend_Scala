import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import scala.concurrent.ExecutionContext

trait  Router {
  def route: Route
}

class MyRouter(shortUrlRepository: ShortUrlRepository)(implicit system: ActorSystem[_],  ex:ExecutionContext)
  extends Router
    with Directives
    with ErrorDirectives
    with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._


  override def route = concat(
    path("ping") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
      }
    },
    pathPrefix("originalUrl"){
      concat(
          get {
            concat(
              pathEnd {
                handleWithGeneric(shortUrlRepository.all()) {
                  urls => complete(urls)
                }
              }
            )
          },
            post {
              entity(as[CreateShortUrl]) { createShortUrl => {
                validateWith(CreateShortUrlValidator)(createShortUrl) {
                  handle(shortUrlRepository.create(createShortUrl))
                  {
                    case InMemoryUrlRepository.DuplicateUrl(_) =>
                      ApiError.duplicateUrl((createShortUrl.originalUrl))
                    case _ =>
                      ApiError.generic
                  }
                  {
                    url => complete(url)
                  }
                }
              }
              }
            },
              path(Segment){ url =>
                concat(
                    get {
                      handle(shortUrlRepository.get(url)) {
                        case InMemoryUrlRepository.NotFound(_) =>
                          ApiError.itemNotFound(url)
                        case _ =>
                          ApiError.generic
                      }
                      { url1 => complete(url1) }
                    }
                )
              }
      )
    }

  )
}