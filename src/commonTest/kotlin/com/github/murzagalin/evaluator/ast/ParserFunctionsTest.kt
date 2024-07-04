package com.github.murzagalin.evaluator.ast

import com.github.murzagalin.evaluator.DefaultFunctions
import com.github.murzagalin.evaluator.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserFunctionsTest {

    private val subject = Parser()

    @Test
    fun parse_function_call_without_arguments() {
        val functionCall = Token.FunctionCall(0, DefaultFunctions.ABS)
        val expression = listOf(
            functionCall,
            Token.Bracket.Left,
            Token.Bracket.Right
        )

        assertEquals(
            Expression.FunctionCall(
                functionCall,
                emptyList()
            ),
            subject.parse(expression)
        )
    }

    @Test
    fun parse_function_call_with_one_argument() {
        val functionCall = Token.FunctionCall(1, DefaultFunctions.MIN)
        val expression = listOf(
            functionCall,
            Token.Bracket.Left,
            Token.Operand.IntNumber(1),
            Token.Bracket.Right
        )

        assertEquals(
            Expression.FunctionCall(
                functionCall,
                listOf(Expression.Terminal(Token.Operand.IntNumber(1)))
            ),
            subject.parse(expression)
        )
    }

    @Test
    fun parse_function_call_with_several_argument() {
        val functionCall = Token.FunctionCall(3, DefaultFunctions.MIN)
        val expression = listOf(
            functionCall,
            Token.Bracket.Left,
            Token.Operand.IntNumber(1),
            Token.FunctionCall.Delimiter,
            Token.Operand.IntNumber(2),
            Token.Operator.Plus,
            Token.Operand.IntNumber(3),
            Token.FunctionCall.Delimiter,
            Token.Operand.IntNumber(4),
            Token.Bracket.Right
        )

        assertEquals(
            Expression.FunctionCall(
                functionCall,
                listOf(
                    Expression.Terminal(Token.Operand.IntNumber(1)),
                    Expression.Binary(
                        Token.Operator.Plus,
                        Expression.Terminal(Token.Operand.IntNumber(2)),
                        Expression.Terminal(Token.Operand.IntNumber(3))
                    ),
                    Expression.Terminal(Token.Operand.IntNumber(4)),
                )
            ),
            subject.parse(expression)
        )
    }

    @Test
    fun expression_with_functions() {
        val variable = Token.Operand.Variable("var")
        val sin = Token.FunctionCall(1, DefaultFunctions.SIN)
        val cos = Token.FunctionCall(1, DefaultFunctions.COS)

        val expression = listOf(
            sin,
            Token.Bracket.Left,
            variable,
            Token.Bracket.Right,
            Token.Operator.Power,
            Token.Operand.IntNumber(2),
            Token.Operator.Plus,
            cos,
            Token.Bracket.Left,
            variable,
            Token.Bracket.Right,
            Token.Operator.Power,
            Token.Operand.IntNumber(2),
        )

        assertEquals(
            Expression.Binary(
                Token.Operator.Plus,
                Expression.Binary(
                    Token.Operator.Power,
                    Expression.FunctionCall(
                        sin,
                        arguments = listOf(Expression.Terminal(variable))
                    ),
                    Expression.Terminal(Token.Operand.IntNumber(2))
                ),
                Expression.Binary(
                    Token.Operator.Power,
                    Expression.FunctionCall(
                        cos,
                        arguments = listOf(Expression.Terminal(variable))
                    ),
                    Expression.Terminal(Token.Operand.IntNumber(2))
                )
            ),
            subject.parse(expression)
        )
    }
}
