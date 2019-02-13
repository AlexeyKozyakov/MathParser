import mathparser.Lexeme
import mathparser.LexemeType
import mathparser.Lexer
import mathparser.Parser
import org.junit.Assert
import org.junit.Test
import java.io.StringReader

class Test {
    @Test
    fun lexerTest() {
        val expression = "-1 + (3 - 43)"
        val lexer = Lexer(StringReader(expression))

        var lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.MINUS, "-"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.NUM, "1"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.PLUS, "+"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.LB, "("), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.NUM, "3"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.MINUS, "-"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.NUM, "43"), lexeme)

        lexeme = lexer.getLexeme()
        Assert.assertEquals(Lexeme(LexemeType.RB, ")"), lexeme)
    }

    @Test
    fun parserTest() {
        val expression = "((-1 * 3 ^ 2 + (3 - 43)) * 3 - 1) / 2"
        val parser = Parser(Lexer(StringReader(expression)))
        Assert.assertEquals(-74, parser.parse().toInt())
    }
}