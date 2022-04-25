package parser.entries

sealed class ParserEntry(private val symbol: Char? = null) {
    override fun toString(): String = symbol.toString()

    companion object {
        const val OPENING_PARENTHESIS: Char = '('
        const val CLOSING_PARENTHESIS: Char = ')'
    }
}

object OpeningParenthesisEntry : ParserEntry(OPENING_PARENTHESIS)

@Suppress("unused")
object ClosingParenthesisEntry : ParserEntry(CLOSING_PARENTHESIS)
