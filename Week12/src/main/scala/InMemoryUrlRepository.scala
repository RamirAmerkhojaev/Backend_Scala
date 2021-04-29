import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.util.hashing.MurmurHash3
import InMemoryUrlRepository.{DuplicateUrl, NotFound}
import com.redis._

object InMemoryUrlRepository {

  final case class DuplicateUrl(url: String) extends Exception(s"Url: $url is already in db!")
  final case class NotFound(url: String) extends Exception(s"Url: $url not found!")
}

class RedisUrlRepository(initialUrls:  Seq[ShortUrl] = Seq.empty)(implicit ec :ExecutionContext) extends ShortUrlRepository {

  private var urls: Vector[ShortUrl] = initialUrls.toVector

  val redis = new RedisClient("localhost", 6379)


  override def all(): Future[Seq[ShortUrl]] = Future.successful(urls)

  override def get(shortUrl: String): Future[String] = {

    redis.get(shortUrl) match {
      case Some(value) => Future.successful(value)
      case None =>
        urls.find(_.shortUrl == shortUrl) match {
          case Some(found) =>
            Future.successful(found.originalUrl)
          case None => Future.failed(NotFound(shortUrl))
        }
    }
  }

  override def create(createUrl: CreateShortUrl): Future[ShortUrl] = {

    val url = ShortUrl(
      id = UUID.randomUUID().toString,
      originalUrl = createUrl.originalUrl,
      shortUrl = MurmurHash3.stringHash(createUrl.originalUrl).toString
    )

    urls.find(t  => t.originalUrl == url.originalUrl) match {
      case Some(_) =>
        Future.failed(DuplicateUrl(url.originalUrl))
      case None =>
        urls = urls :+ url
        redis.set(url.shortUrl, url.originalUrl)
        Future.successful(url)
    }
  }



}


