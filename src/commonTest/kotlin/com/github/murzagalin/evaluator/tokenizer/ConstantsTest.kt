package com.github.murzagalin.evaluator.tokenizer

import com.github.murzagalin.evaluator.DefaultFunctions
import com.github.murzagalin.evaluator.Token
import com.github.murzagalin.evaluator.Tokenizer
import kotlin.math.E
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConstantsTest {
    private val subject = Tokenizer()

    @Test
    fun simple_constants() {
        assertContentEquals(
            listOf(Token.Operand.DoubleNumber(PI)),
            subject.tokenize("pi")
        )
        assertContentEquals(
            listOf(Token.Operand.DoubleNumber(E)),
            subject.tokenize("e")
        )
    }

    @Test
    fun functions_with_constant_names() {
        assertFailsWith<IllegalArgumentException> { subject.tokenize("pi()") }
        assertFailsWith<IllegalArgumentException> { subject.tokenize("e()") }
    }

    @Test
    fun expressions_with_functions() {
        assertEquals(
            listOf(
                Token.FunctionCall(1, DefaultFunctions.SIN),
                Token.Bracket.Left,
                Token.Operand.DoubleNumber(PI),
                Token.Bracket.Right
            ),
            subject.tokenize("sin(pi)")
        )
        assertEquals(
            listOf(
                Token.FunctionCall(1, DefaultFunctions.LN),
                Token.Bracket.Left,
                Token.Operand.DoubleNumber(E),
                Token.Bracket.Right
            ),
            subject.tokenize("ln(e)")
        )
    }
}
