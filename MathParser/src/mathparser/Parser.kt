package mathparser

import exceptions.SemanticException
import kotlin.math.pow

class Parser(private val lexer: Lexer) {

    private lateinit var current : Lexeme

    fun parse() : Double {
        nextLexeme()
        return parseExpr()
    }

    private fun parseExpr() : Double {
        var tmp = parseTerm()
        while (current.type == LexemeType.PLUS || current.type == LexemeType.MINUS) {
            val type = current.type
            nextLexeme()
            when(type) {
                LexemeType.PLUS -> tmp += parseTerm()
                LexemeType.MINUS -> tmp -= parseTerm()
                else -> throw SemanticException()
            }
        }
        return tmp
    }

    private fun parseTerm() : Double {
        var tmp = parseFactor()
        while (current.type == LexemeType.MUL || current.type == LexemeType.DIV) {
            val type = current.type
            nextLexeme()
            when(type) {
                LexemeType.MUL -> tmp *= parseFactor()
                LexemeType.DIV -> tmp /= parseFactor()
                else -> throw SemanticException()
            }
        }
        return tmp
    }

    private fun parseFactor() : Double {
        var tmp = parsePower()
        if (current.type == LexemeType.POW) {
            nextLexeme()
            return tmp.pow(parseFactor())
        }
        return tmp
    }

    private fun parsePower() : Double {
        return if (current.type == LexemeType.MINUS) {
            nextLexeme()
            -parseAtom()
        } else {
            parseAtom()
        }
    }

    private fun parseAtom() : Double {
         when(current.type) {
            LexemeType.LB -> {
                nextLexeme()
                val tmp = parseExpr()
                if (current.type != LexemeType.RB) {
                    throw SemanticException()
                }
                nextLexeme()
                return tmp
            }
            LexemeType.NUM -> {
                val tmp = current.text.toDouble()
                nextLexeme()
                return tmp
            }
            else -> throw SemanticException()
        }
    }

    private fun nextLexeme() {
        current = lexer.getLexeme()
    }
}