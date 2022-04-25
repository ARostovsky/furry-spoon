package solver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.entries.*

class SolverTest {

    @Test
    fun addition() {
        val inputEntries = listOf(NumberEntry(1), NumberEntry(2), AddOperation)
        doTest(inputEntries, 3)
    }

    @Test
    fun `addition with unary minus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryMinus, NumberEntry(2), AddOperation)
        doTest(inputEntries, 1)
    }

    @Test
    fun `addition with unary plus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryPlus, NumberEntry(2), AddOperation)
        doTest(inputEntries, 3)
    }

    @Test
    fun subtraction() {
        val inputEntries = listOf(NumberEntry(1), NumberEntry(2), SubOperation)
        doTest(inputEntries, -1)
    }

    @Test
    fun `subtraction with unary minus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryMinus, NumberEntry(2), SubOperation)
        doTest(inputEntries, -3)
    }

    @Test
    fun `subtraction with unary plus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryPlus, NumberEntry(2), SubOperation)
        doTest(inputEntries, -1)
    }

    @Test
    fun multiplication() {
        val inputEntries = listOf(NumberEntry(1), NumberEntry(2), MulOperation)
        doTest(inputEntries, 2)
    }

    @Test
    fun `multiplication with unary minus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryMinus, NumberEntry(2), MulOperation)
        doTest(inputEntries, -2)
    }

    @Test
    fun `multiplication with unary plus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryPlus, NumberEntry(2), MulOperation)
        doTest(inputEntries, 2)
    }

    @Test
    fun division() {
        val inputEntries = listOf(NumberEntry(1), NumberEntry(2), DivOperation)
        doTest(inputEntries, 0.5)
    }

    @Test
    fun `division with unary minus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryMinus, NumberEntry(2), DivOperation)
        doTest(inputEntries, -0.5)
    }

    @Test
    fun `division with unary plus`() {
        val inputEntries = listOf(NumberEntry(1), UnaryPlus, NumberEntry(2), DivOperation)
        doTest(inputEntries, 0.5)
    }

    @Test
    fun `unary plus`() {
        val inputEntries = listOf(NumberEntry(10), UnaryPlus)
        doTest(inputEntries, 10)
    }

    @Test
    fun `unary plus in parenthesis`() {
        val solverResult = Solver.solve("(+10)")
        assertEquals(10, solverResult.toInt())
    }

    @Test
    fun `several unary pluses`() {
        val solverResult = Solver.solve("(+++10)")
        assertEquals(10, solverResult.toInt())
    }

    @Test
    fun `unary minus`() {
        val inputEntries = listOf(NumberEntry(10), UnaryMinus)
        doTest(inputEntries, -10)
    }

    @Test
    fun `unary minus in parenthesis`() {
        val solverResult = Solver.solve("(-10)")
        assertEquals(-10, solverResult.toInt())
    }

    @Test
    fun `several unary minuses`() {
        val solverResult = Solver.solve("(---10)")
        assertEquals(-10, solverResult.toInt())
    }

    @Test
    fun `mixed several unary pluses and minuses`() {
        val solverResult = Solver.solve("-+--+10")
        assertEquals(-10, solverResult.toInt())
    }

    @Test
    fun `example test 1`() {
        val solverResult = Solver.solve("(1+2)*4+3")
        assertEquals(15, solverResult.toInt())
    }

    @Test
    fun `example test 2`() {
        val solverResult = Solver.solve("3 + 4 * 2 / (1 - 5)")
        assertEquals(1, solverResult.toInt())
    }

    private fun doTest(inputEntries: List<ParserEntry>, expected: Double) {
        val solverResult = Solver.solve(inputEntries)
        assertEquals(expected, solverResult.value)
    }

    private fun doTest(inputEntries: List<ParserEntry>, expected: Int) {
        val solverResult = Solver.solve(inputEntries)
        assertEquals(expected, solverResult.value.toInt())
    }
}