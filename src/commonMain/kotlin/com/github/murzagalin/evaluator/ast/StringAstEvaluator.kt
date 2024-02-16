package com.github.murzagalin.evaluator.ast

internal class StringAstEvaluator {

    fun evaluate(expression: Expression, values: Map<String, Any> = emptyMap()): String {
        val baseEvaluator = AstEvaluator(values)
        val evaluated = baseEvaluator.evaluate(expression)
        return evaluated.toString()
    }
}
