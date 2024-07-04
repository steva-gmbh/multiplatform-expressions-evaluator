package com.github.murzagalin.evaluator.ast

import com.github.murzagalin.evaluator.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserBooleanTests {

    private val subject = Parser()

    @Test
    fun simple_or_sequence() {
        val expression = listOf(
            Token.Operand.Boolean(false),
            Token.Operator.Or,
            Token.Operand.Boolean(true),
            Token.Operator.Or,
            Token.Operand.Boolean(true)
        )
        val result = subject.parse(expression)

        assertEquals(
            Expression.Binary(
                Token.Operator.Or,
                Expression.Binary(
                    Token.Operator.Or,
                    Expression.Terminal(Token.Operand.Boolean(false)),
                    Expression.Terminal(Token.Operand.Boolean(true))
                ),
                Expression.Terminal(Token.Operand.Boolean(true))
            ),
            result
        )
    }


    @Test
    fun simple_and_sequence() {
        val expression = listOf(
            Token.Operand.Boolean(false),
            Token.Operator.And,
            Token.Operand.Boolean(true),
            Token.Operator.And,
            Token.Operand.Boolean(true)
        )
        val result = subject.parse(expression)

        assertEquals(
            Expression.Binary(
                Token.Operator.And,
                Expression.Binary(
                    Token.Operator.And,
                    Expression.Terminal(Token.Operand.Boolean(false)),
                    Expression.Terminal(Token.Operand.Boolean(true))
                ),
                Expression.Terminal(Token.Operand.Boolean(true))
            ),
            result
        )
    }

    @Test
    fun simple_and_or_sequence() {
        val expression1 = listOf(
            Token.Operand.Boolean(false),
            Token.Operator.And,
            Token.Operand.Boolean(true),
            Token.Operator.Or,
            Token.Operand.Boolean(true)
        )
        val expression2 = listOf(
            Token.Operand.Boolean(false),
            Token.Operator.Or,
            Token.Operand.Boolean(true),
            Token.Operator.And,
            Token.Operand.Boolean(true)
        )

        assertEquals(
            Expression.Binary(
                Token.Operator.Or,
                Expression.Binary(
                    Token.Operator.And,
                    Expression.Terminal(Token.Operand.Boolean(false)),
                    Expression.Terminal(Token.Operand.Boolean(true))
                ),
                Expression.Terminal(Token.Operand.Boolean(true))
            ),
            subject.parse(expression1)
        )

        assertEquals(
            Expression.Binary(
                Token.Operator.Or,
                Expression.Terminal(Token.Operand.Boolean(false)),
                Expression.Binary(
                    Token.Operator.And,
                    Expression.Terminal(Token.Operand.Boolean(true)),
                    Expression.Terminal(Token.Operand.Boolean(true))
                )
            ),
            subject.parse(expression2)
        )
    }

    @Test
    fun comparison_equality_combination() {
        val expression = listOf(
            Token.Operand.IntNumber(1),
            Token.Operator.LessThan,
            Token.Operand.IntNumber(2),
            Token.Operator.And,
            Token.Operand.IntNumber(2),
            Token.Operator.GreaterEqualThan,
            Token.Operand.IntNumber(3),
            Token.Operator.Or,
            Token.Operand.IntNumber(4),
            Token.Operator.Equal,
            Token.Operand.IntNumber(5)
        )

        assertEquals(
            Expression.Binary(
                Token.Operator.Or,
                Expression.Binary(
                    Token.Operator.And,
                    Expression.Binary(
                        Token.Operator.LessThan,
                        Expression.Terminal(Token.Operand.IntNumber(1)),
                        Expression.Terminal(Token.Operand.IntNumber(2))
                    ),
                    Expression.Binary(
                        Token.Operator.GreaterEqualThan,
                        Expression.Terminal(Token.Operand.IntNumber(2)),
                        Expression.Terminal(Token.Operand.IntNumber(3))
                    ),
                ),
                Expression.Binary(
                    Token.Operator.Equal,
                    Expression.Terminal(Token.Operand.IntNumber(4)),
                    Expression.Terminal(Token.Operand.IntNumber(5))
                )
            ),
            subject.parse(expression)
        )
    }
}
