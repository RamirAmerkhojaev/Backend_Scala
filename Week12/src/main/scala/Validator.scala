import java.net.{MalformedURLException, URL}

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateShortUrlValidator extends Validator[CreateShortUrl] {
  def validate(createUrl: CreateShortUrl): Option[ApiError] = {
    if (isValidURL(createUrl.originalUrl)) None
    else Some(ApiError.invalidUrl)
  }

  def isValidURL(urlStr: String): Boolean = try {
    new URL(urlStr)
    true
  } catch {
    case _: MalformedURLException =>
      false
  }
}

