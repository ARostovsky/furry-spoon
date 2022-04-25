package parser.entries

sealed class OperationEntry(symbol: Char, private val priority: Byte) :
    ParserEntry(symbol), Comparable<OperationEntry> {
    override fun compareTo(other: OperationEntry): Int = -priority.compareTo(other.priority)

    companion object {
        const val ADD: Char = '+'
        const val SUB: Char = '-'
        const val MUL: Char = '*'
        const val DIV: Char = '/'
    }
}
