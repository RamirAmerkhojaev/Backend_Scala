import scala.concurrent.Future

trait ShortUrlRepository {

  def all(): Future[Seq[ShortUrl]]
  def get(id: String): Future[String]
  def create(createUrl: CreateShortUrl): Future[ShortUrl]


}
