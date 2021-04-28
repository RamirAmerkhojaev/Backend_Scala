trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateTodoValidator extends Validator[CreateTodo] {
  def validate(createTodo: CreateTodo): Option[ApiError] = {
    if (createTodo.title.isEmpty) Some(ApiError.emptyTitleField)
    else if (createTodo.description.isEmpty) Some(ApiError.emptyDescriptionField)
    else None
  }
}

object UpdateTodoValidator extends Validator[UpdateTodo] {
  def validate(updateTodo: UpdateTodo): Option[ApiError] = {
    if (updateTodo.title.exists(_.isEmpty)) Some(ApiError.emptyTitleField)
    else if (updateTodo.description.isEmpty) Some(ApiError.emptyDescriptionField)
    else None
  }
}

//todo: update validator