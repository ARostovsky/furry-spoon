package parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.entries.*
import parser.exceptions.ParenthesisMissingException

class ParserTest {
    @Test
    fun `integer number`() = doTest("1234", "1234")

    @Test
    fun `negative integer number`() {
        doTest("- 1234", "1234 -") { list -> list.last() is UnaryMinus }
    }

    @Test
    fun `integer number with leading unary plus`() {
        doTest("+ 1234", "1234 +") { list -> list.last() is UnaryPlus }
    }

    @Test
    fun `integer number with several unary pluses`() {
        doTest("+++ 1234", "1234 + + +") { list -> list.last() is UnaryPlus }
    }

    @Test
    fun `float number with dot`() = doTest("1.50", "1.5")

    @Test
    fun `float number with comma`() = doTest("2,80", "2.8")

    @Test
    fun `negative float number`() {
        doTest("- 2,80", "2.8 -") { list -> list.last() is UnaryMinus }
    }

    @Test
    fun addition() = doTest("13 + 2.5", "13 2.5 +") { list -> list.last() is AddOperation }

    @Test
    fun `addition with negative number`() {
        doTest("- 13 + 2.5", "13 - 2.5 +") { list ->
            list[1] is UnaryMinus && list.last() is AddOperation
        }
    }

    @Test
    fun subtraction() = doTest("13 - 2.5", "13 2.5 -") { list -> list.last() is SubOperation }

    @Test
    fun `subtraction with negative number`() {
        doTest("- 13 - 2.5", "13 - 2.5 -") { list ->
            list[1] is UnaryMinus && list.last() is SubOperation
        }
    }

    @Test
    fun multiplication() = doTest("13 * 2.5", "13 2.5 *")

    @Test
    fun `multiplication with negative number`() {
        doTest("- 13 * 2.5", "13 - 2.5 *") { list ->
            list[1] is UnaryMinus && list.last() is MulOperation
        }
    }

    @Test
    fun division() = doTest("13 / 2.5", "13 2.5 /")

    @Test
    fun `division with negative number`() {
        doTest("- 13 / 2.5", "13 - 2.5 /") { list ->
            list[1] is UnaryMinus && list.last() is DivOperation
        }
    }

    @Test
    fun `leading parenthesis`() = doTest("(1.7 + 3) * 2", "1.7 3 + 2 *")

    @Test
    fun `trailing parenthesis`() = doTest("2.5 / (1 + 3)", "2.5 1 3 + /")

    @Test
    fun `recurring parenthesis 1`() = doTest("((700 -1))", "700 1 -")

    @Test
    fun `single number inside parenthesis`() = doTest("((1)*((2)*(700 -1)))", "1 2 700 1 - * *")

    @Test
    fun `missing opening parenthesis`() {
        val parser = ParserImpl("1 + 2)")
        assertThrows<ParenthesisMissingException> { parser.parse() }
    }

    @Test
    fun `missing closing parenthesis`() {
        val parser = ParserImpl("(1 + (2)")
        assertThrows<ParenthesisMissingException> { parser.parse() }
    }

    @Test
    fun `test example 1`() = doTest("(1+2)*4+3", "1 2 + 4 * 3 +")

    @Test
    fun `test example 2`() {
        doTest("3 + 4 * 2 / (1 - 5)", "3 4 2 * 1 5 - / +") { list ->
            list.last() is AddOperation
        }
    }

    private fun doTest(input: String, expected: String) {
        val parser = ParserImpl(input)
        val result = parser.parse().joinToString(" ")
        assertEquals(expected, result)
    }

    private fun doTest(input: String, expected: String, additionalCheck: (list: List<ParserEntry>) -> Boolean) {
        val parser = ParserImpl(input)
        val result = parser.parse()
        assertEquals(expected, result.joinToString(" "))
        assertTrue(additionalCheck(result))
    }
}