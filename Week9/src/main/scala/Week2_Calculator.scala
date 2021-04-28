import fastparse._
import NoWhitespace._


object Week2_Calculator {

  def sign(ast: (Double, Seq[(String, Double)])): Double = {
    val (base, ops) = ast
    ops.foldLeft(base) {
      case (n1, (op, n2)) =>
        op match {
          case "*" => n1 * n2
          case ":" => n1 / n2
          case "+" => n1 + n2
          case "-" => n1 - n2
        }
    }
  }

  def number[_: P]: P[Double] = P( CharIn("0-9").rep(1).!.map(_.toDouble) )
  def divMul[_: P]: P[Double] = P( number ~ (CharIn("*:").! ~/ number).rep ).map(sign)
  def addSub[_: P]: P[Double] = P( divMul ~ (CharIn("+\\-").! ~/ divMul).rep ).map(sign)
  def expr[_: P]: P[Double]   = P( addSub ~ "=" ~ End )

  def process(line: String): String  = {
    if (line.isEmpty) return ""

    parse(line, expr(_)) match {
      case Parsed.Success(result, _) => s"$result"
      case Parsed.Failure(error) => s"Input Error: $error"
    }
  }
}

