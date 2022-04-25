import parser.entries.NumberEntry
import parser.entries.ParserEntry
import solver.exceptions.StackUnexpectedlyEmptyException
import java.util.*

object Utils {
    @JvmStatic
    fun Char.isPartOfNumber(): Boolean = isDigit() || this in setOf('.', ',')

    @JvmStatic
    fun Stack<ParserEntry>.peekOrNull(): ParserEntry? = if (isEmpty()) null else peek()

    @JvmStatic
    fun Stack<NumberEntry>.popOrException(): NumberEntry {
        if (isEmpty()) throw StackUnexpectedlyEmptyException()
        return pop()
    }
}