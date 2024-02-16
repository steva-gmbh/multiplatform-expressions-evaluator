package com.github.murzagalin.evaluator.ast

import com.github.murzagalin.evaluator.Convert

internal class IntAstEvaluator {

    fun evaluate(expression: Expression, values: Map<String, Any> = emptyMap()): Int {
        val baseEvaluator = AstEvaluator(values)
        val evaluated = baseEvaluator.evaluate(expression)
        return Convert.toInt(evaluated)
    }
}
