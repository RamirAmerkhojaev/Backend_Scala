import UrlRepository.UrlDoesNotExist
import com.redis.RedisClient
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala._
import Helpers.GenericObservable
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters._

import java.util.UUID
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.hashing.MurmurHash3



object UrlRepository {

  final case class UrlDoesNotExist(urlPath: String) extends Exception("")

}

class MongoDbUrlRepository(implicit ec: ExecutionContext) extends ShortUrlRepository {

  private val codecRegistry = fromRegistries(fromProviders(classOf[ShortUrl]), DEFAULT_CODEC_REGISTRY)
  private val MONGO_URI: String = "mongodb://localhost:27017"

  val redis = new RedisClient("localhost", 6379)

  private val mongoClient: MongoClient = MongoClient(MONGO_URI)
  private val database: MongoDatabase = mongoClient.getDatabase("database").withCodecRegistry(codecRegistry)
  private val collection: MongoCollection[ShortUrl] = database.getCollection("urls")

  override def create(createUrl: CreateShortUrl): Future[ShortUrl] = {
        val url = ShortUrl(
          id = UUID.randomUUID().toString,
          originalUrl = createUrl.originalUrl,
          shortUrl = MurmurHash3.stringHash(createUrl.originalUrl).toString
        )
        collection.insertOne(url).results()
        redis.set(url.shortUrl, url.originalUrl)
    Future.successful(url)
  }

  override def get(shortUrl: String): Future[String] = {
    redis.get(shortUrl) match {
      case Some(value) => Future.successful(value)
      case None =>
        collection.find(equal("shortUrl", shortUrl)).headResult() match {
          case url: ShortUrl =>

            Future.successful(url.originalUrl)
          case _ =>
            Future.failed(UrlDoesNotExist(shortUrl))
        }
    }
  }


  override def all(): Future[Seq[ShortUrl]] = {
    collection.find().toFuture()
  }



}

