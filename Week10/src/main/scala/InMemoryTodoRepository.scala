import InMemoryTodoRepository.{TodoDuplicateTitle, TodoNotFound}

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

object InMemoryTodoRepository {

  final case class TodoNotFound(id: String) extends Exception(s"Todo with $id not found!")
  final case class TodoDuplicateTitle(title: String) extends Exception(s"Todo with $title found!")

}

class InMemoryTodoRepository(initialTodos:Seq[Todo] = Seq.empty)(implicit ec:ExecutionContext) extends TodoRepository {

  private var todos: Vector[Todo] = initialTodos.toVector

  override def all(): Future[Seq[Todo]] = Future.successful(todos)

  override def done(): Future[Seq[Todo]] = Future.successful(todos.filter(_.done))

  override def pending(): Future[Seq[Todo]] = Future.successful(todos.filterNot(_.done))

  override def create(createTodo: CreateTodo): Future[Todo] = {
      val todo = Todo(
        id = UUID.randomUUID().toString,
        title = createTodo.title,
        description = createTodo.description,
        done = false
      )
      todos.find(t   => t.title == todo.title) match {
        case Some(_) =>
          Future.failed(TodoDuplicateTitle(todo.title))
        case None =>
          todos = todos :+ todo
          Future.successful(todo)
      }
  }

  override def update(id: String, updateTodo: UpdateTodo): Future[Todo] = {
    todos.find(_.id == id) match {
      case Some(founded) =>
        val newTodo = updateHelper(founded, updateTodo)
        todos = todos.map(t => if (t.id == id) newTodo else t)
        Future.successful(newTodo)
      case None => Future.failed(TodoNotFound(id))
    }
  }

  private def updateHelper(todo: Todo, updateTodo: UpdateTodo): Todo ={
    val t1 = updateTodo.title.map(title => todo.copy(title = title)).getOrElse(todo)
    val t2 = updateTodo.description.map(description => t1.copy(description = description)).getOrElse(t1)
    updateTodo.done.map(done => t2.copy(done = done)).getOrElse(t2)

  }

}
