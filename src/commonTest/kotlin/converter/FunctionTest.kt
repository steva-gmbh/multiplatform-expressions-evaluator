package converter

import Converter
import kotlin.test.Test
import kotlin.test.assertContentEquals

class FunctionTest {

    private val subject = Converter()

    @Test
    fun simple_function_call() {
        val expression = listOf(
            Token.Function.Cos,
            Token.Bracket.Left,
            Token.Operand.Num(10.0),
            Token.Bracket.Right
        )
        val result = subject.convert(expression)

        assertContentEquals(
            listOf(
                Token.Operand.Num(10.0),
                Token.Function.Cos
            ),
            result
        )
    }

    @Test
    fun sum_and_function_call() {
        val expression = listOf(
            Token.Operand.Num(1.0),
            Token.Operator.Sum,
            Token.Function.Cos,
            Token.Bracket.Left,
            Token.Operand.Num(10.0),
            Token.Bracket.Right
        )
        val result = subject.convert(expression)

        assertContentEquals(
            listOf(
                Token.Operand.Num(1.0),
                Token.Operand.Num(10.0),
                Token.Function.Cos,
                Token.Operator.Sum
            ),
            result
        )
    }

    @Test
    fun function_in_a_function() {
        val expression = listOf(
            Token.Function.Cos,
            Token.Bracket.Left,
            Token.Function.Ln,
            Token.Bracket.Left,
            Token.Operand.Num(10.0),
            Token.Bracket.Right,
            Token.Bracket.Right
        )
        val result = subject.convert(expression)

        assertContentEquals(
            listOf(
                Token.Operand.Num(10.0),
                Token.Function.Ln,
                Token.Function.Cos
            ),
            result
        )
    }

    @Test
    fun function_with_two_params() {
        val expression = listOf(
            Token.Function.Log,
            Token.Bracket.Left,
            Token.Operand.Num(10.0),
            Token.Function.Delimiter,
            Token.Operand.Num(12.0),
            Token.Bracket.Right
        )

        assertContentEquals(
            listOf(
                Token.Operand.Num(10.0),
                Token.Operand.Num(12.0),
                Token.Function.Log
            ),
            subject.convert(expression)
        )
    }

    @Test
    fun multi_var_function_with_calculated_first_param() {
        val expression = listOf(
            Token.Function.Log,
            Token.Bracket.Left,
            Token.Operand.Num(2),
            Token.Operator.Mult,
            Token.Operand.Num(2),
            Token.Function.Delimiter,
            Token.Operand.Num(3),
            Token.Bracket.Right
        )
        assertContentEquals(
            listOf(
                Token.Operand.Num(2),
                Token.Operand.Num(2),
                Token.Operator.Mult,
                Token.Operand.Num(3),
                Token.Function.Log
            ),
            subject.convert(expression)
        )
    }
}