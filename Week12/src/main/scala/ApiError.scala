import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(statusCode: StatusCode, message: String): ApiError =
    new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")
  val invalidUrl: ApiError = new ApiError(StatusCodes.BadRequest, "Duplicate url error.")

  def itemNotFound(url: String): ApiError =
    new ApiError(StatusCodes.NotFound, s"The url $url could not be found.")

  def duplicateUrl(url: String): ApiError =
    new ApiError(StatusCodes.BadRequest, s"Url $url already exists!")
}