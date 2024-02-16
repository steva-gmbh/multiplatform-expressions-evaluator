package com.github.murzagalin.evaluator

object Convert {
    fun toBoolean(value: Any?) = when (value) {
        null -> false
        is Boolean -> value
        is Int -> value != 0
        is Double -> value != 0
        "true" -> true
        "on" -> true
        else -> false
    }

    fun toInt(value: Any?) = when (value) {
        null -> 0
        is Int -> value
        is Double -> value.toInt()
        else -> value.toString().toIntOrNull() ?: 0
    }

    fun toDouble(value: Any?) = when (value) {
        null -> 0.0
        is Double -> value
        is Int -> value.toDouble()
        else -> value.toString().toDoubleOrNull() ?: 0.0
    }

    fun toString(value: Any?) = when (value) {
        null -> ""
        is String -> value
        else -> value.toString()
    }
}
