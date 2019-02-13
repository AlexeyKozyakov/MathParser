package mathparser

import exceptions.SyntaxException
import java.io.Reader
import java.lang.StringBuilder

class Lexer(private val reader : Reader) {

    private var current : Char = '0'

    private var lastDigit : Boolean = false

    fun getLexeme() : Lexeme {
        readNext()
        lastDigit = false
        if (checkEnd()) {
            return Lexeme(LexemeType.EOF, "EOF")
        }
        skipWhiteSpaces()
        return when {
            current == '+' -> Lexeme(LexemeType.PLUS, "+")
            current == '-' -> Lexeme(LexemeType.MINUS, "-")
            current == '*' -> Lexeme(LexemeType.MUL, "*")
            current == '/' -> Lexeme(LexemeType.DIV, "/")
            current == '^' -> Lexeme(LexemeType.POW, "^")
            current == '(' -> Lexeme(LexemeType.LB, "(")
            current == ')' -> Lexeme(LexemeType.RB, ")")
            current.isDigit() -> Lexeme(LexemeType.NUM, readDigit())
            else -> throw SyntaxException()
        }
    }

    private fun readNext() {
        if (!lastDigit) {
            current = reader.read().toChar()
        }
    }

    private fun checkEnd() : Boolean {
        return current == (-1).toChar()
    }

    private fun skipWhiteSpaces() {
        while (!checkEnd() && current.isWhitespace()) {
            readNext()
        }
    }

    private fun readDigit() : String {
        val builder = StringBuilder()
        while (current.isDigit()) {
            skipWhiteSpaces()
            builder.append(current)
            readNext()
        }
        lastDigit = true
        return builder.toString()
    }

}