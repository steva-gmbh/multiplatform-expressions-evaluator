package com.github.murzagalin.evaluator.ast

import com.github.murzagalin.evaluator.Convert

internal class DoubleAstEvaluator {

    fun evaluate(expression: Expression, values: Map<String, Any> = emptyMap()): Double {
        val baseEvaluator = AstEvaluator(values)
        val evaluated = baseEvaluator.evaluate(expression)
        return Convert.toDouble(evaluated)
    }
}
