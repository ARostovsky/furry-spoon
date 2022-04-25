package parser.entries

import parser.exceptions.FractionParsingException

class NumberEntry(val value: Double) : ParserEntry() {
    constructor(value: Long) : this(value.toDouble())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NumberEntry

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    companion object {
        fun create(string: String): NumberEntry {
            val value = string
                .replace(",", ".")
                .toDoubleOrNull() ?: throw FractionParsingException()
            return NumberEntry(value)
        }
    }
}