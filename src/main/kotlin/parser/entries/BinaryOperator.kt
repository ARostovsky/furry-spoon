package parser.entries

sealed class BinaryOperator(symbol: Char, priority: Byte) : OperationEntry(symbol, priority) {

    abstract fun doOperation(left: NumberEntry, right: NumberEntry): NumberEntry

    companion object {
        fun get(symbol: Char): BinaryOperator = when (symbol) {
            ADD -> AddOperation
            SUB -> SubOperation
            MUL -> MulOperation
            DIV -> DivOperation
            else -> error("Unexpected operator $symbol")
        }
    }
}

object AddOperation : BinaryOperator(ADD, 3) {
    override fun doOperation(left: NumberEntry, right: NumberEntry): NumberEntry =
        NumberEntry(left.value + right.value)
}

object SubOperation : BinaryOperator(SUB, 3) {
    override fun doOperation(left: NumberEntry, right: NumberEntry): NumberEntry =
        NumberEntry(left.value - right.value)
}

object MulOperation : BinaryOperator(MUL, 2) {
    override fun doOperation(left: NumberEntry, right: NumberEntry): NumberEntry =
        NumberEntry(left.value * right.value)
}

object DivOperation : BinaryOperator(DIV, 2) {
    override fun doOperation(left: NumberEntry, right: NumberEntry): NumberEntry =
        NumberEntry(left.value / right.value)
}