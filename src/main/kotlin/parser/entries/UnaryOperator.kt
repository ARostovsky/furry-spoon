package parser.entries

sealed class UnaryOperator(symbol: Char, priority: Byte) : OperationEntry(symbol, priority) {
    abstract fun doOperation(entry: NumberEntry): NumberEntry

    companion object {
        fun get(symbol: Char): UnaryOperator = when (symbol) {
            ADD -> UnaryPlus
            SUB -> UnaryMinus
            else -> error("Unexpected operator $symbol")
        }
    }
}

object UnaryPlus : UnaryOperator(ADD, 1) {
    override fun doOperation(entry: NumberEntry): NumberEntry = NumberEntry(entry.value.unaryPlus())
}

object UnaryMinus : UnaryOperator(SUB, 1) {
    override fun doOperation(entry: NumberEntry): NumberEntry = NumberEntry(entry.value.unaryMinus())
}