package com.github.murzagalin.evaluator.ast

import com.github.murzagalin.evaluator.Convert

internal class BooleanAstEvaluator {

    fun evaluate(expression: Expression, values: Map<String, Any> = emptyMap()): Boolean {
        val baseEvaluator = AstEvaluator(values)
        val evaluated = baseEvaluator.evaluate(expression)
        return Convert.toBoolean(evaluated)
    }
}
