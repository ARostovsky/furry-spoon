package parser

import parser.entries.*
import parser.entries.OperationEntry.Companion.ADD
import parser.entries.OperationEntry.Companion.DIV
import parser.entries.OperationEntry.Companion.MUL
import parser.entries.OperationEntry.Companion.SUB
import parser.entries.ParserEntry.Companion.CLOSING_PARENTHESIS
import parser.entries.ParserEntry.Companion.OPENING_PARENTHESIS
import parser.exceptions.ParenthesisMissingException
import Utils.isPartOfNumber
import Utils.peekOrNull
import java.util.*
import kotlin.collections.ArrayDeque

class ParserImpl(input: String) : Parser {
    private val stack = Stack<ParserEntry>()
    private val queue = ArrayDeque<Char>()

    init {
        queue.addAll(input.toCharArray().filterNot { it.isWhitespace() }.toList())
    }

    override fun parse(): List<ParserEntry> {
        val result = mutableListOf<ParserEntry>()

        var symbol: Char
        while (queue.isNotEmpty()) {
            symbol = queue.removeFirst()

            when {
                symbol.isPartOfNumber() -> {
                    val numberStringBuilder = StringBuilder()
                    numberStringBuilder.append(symbol)
                    while (queue.isNotEmpty() && queue.first().isPartOfNumber()) {
                        numberStringBuilder.append(queue.removeFirst())
                    }

                    val number = NumberEntry.create(numberStringBuilder.toString())
                    result.add(number)
                }
                symbol == OPENING_PARENTHESIS -> {
                    stack.add(OpeningParenthesisEntry)
                }
                symbol == CLOSING_PARENTHESIS -> {
                    while (stack.peek() != OpeningParenthesisEntry) {
                        result.add(stack.pop())
                        if (stack.isEmpty()) throw ParenthesisMissingException()
                    }
                    stack.pop()
                }
                symbol in SUPPORTED_OPERATIONS -> {
                    if (
                        symbol in UNARY_OPERATIONS
                        && stack.let { it.isEmpty() || it.peekOrNull() == OpeningParenthesisEntry || it.peekOrNull() is UnaryOperator }
                        && result.lastOrNull()?.let { it !is NumberEntry } != false
                    ) {
                        val unaryOperator = UnaryOperator.get(symbol)
                        stack.add(unaryOperator)
                    } else {
                        val binaryOperator = BinaryOperator.get(symbol)
                        while (stack.peekOrNull().let { it is OperationEntry && it >= binaryOperator }) {
                            result.add(stack.pop())
                        }
                        stack.add(binaryOperator)
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            if (stack.peek() !is OperationEntry) throw ParenthesisMissingException()
            result.add(stack.pop())
        }
        return result
    }

    companion object {
        private val SUPPORTED_OPERATIONS: Set<Char> = setOf(ADD, SUB, MUL, DIV) // '^'?
        private val UNARY_OPERATIONS: Set<Char> = setOf(ADD, SUB)
    }
}


