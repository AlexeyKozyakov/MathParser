import mathparser.Lexer
import mathparser.Parser
import java.io.StringReader

fun main() {
    val expression = "((-1 * 3 ^ 2 + (3 - 43)) * 3 - 1) / 2"
    val parser = Parser(Lexer(StringReader(expression)))
    print("((-1 * 3 ^ 2 + (3 - 43)) * 3 - 1) / 2 =  ${parser.parse()}")
}