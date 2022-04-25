package parser

import parser.entries.ParserEntry

interface Parser {
    fun parse(): List<ParserEntry>
}