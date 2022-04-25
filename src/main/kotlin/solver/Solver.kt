package solver

import Utils.popOrException
import parser.ParserImpl
import parser.entries.BinaryOperator
import parser.entries.NumberEntry
import parser.entries.ParserEntry
import parser.entries.UnaryOperator
import solver.exceptions.IllegalEntryException
import java.util.*

object Solver {
    fun solve(input: String): String {
        val parser = ParserImpl(input)
        val parsedInput = parser.parse()
        return solve(parsedInput).toString()
    }

    fun solve(parsedInput: List<ParserEntry>): NumberEntry {
        val stack = Stack<NumberEntry>()

        parsedInput.forEach { entry ->
            when (entry) {
                is NumberEntry -> {
                    stack.push(entry)
                }
                is UnaryOperator -> {
                    val value = stack.popOrException()
                    val result = entry.doOperation(value)
                    stack.push(result)
                }
                is BinaryOperator -> {
                    val right = stack.popOrException()
                    val left = stack.popOrException()

                    val result = entry.doOperation(left, right)
                    stack.push(result)
                }
                else -> throw IllegalEntryException()
            }
        }

        return stack.popOrException()
    }
}
