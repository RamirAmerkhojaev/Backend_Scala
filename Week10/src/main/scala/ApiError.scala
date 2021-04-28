import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")
  val emptyTitleField: ApiError = new ApiError(StatusCodes.BadRequest, message = "Empty title.")
  val emptyDescriptionField: ApiError = new ApiError(StatusCodes.BadRequest, message = "Empty Description.")

  def duplicateTitle(title: String): ApiError =
    new ApiError(StatusCodes.BadRequest, s"Todo with $title already exists!")

  def todoNotFound(title: String): ApiError =
    new ApiError(StatusCodes.NotFound, s"Todo with $title could not be found!")

}