package com.example.data.models

import com.example.domain.models.ExampleModel
import kotlinx.serialization.Serializable

@Serializable
data class GameSessionModel(
    val game_mode: String,
    val duration: Long,
    val math_operations: List<String>,
    val examples_category: Int,
    val examples: List<ExampleApiModel>,
    val total_count: Int,
    val correct_count: Int
)

@Serializable
data class ExampleApiModel(
    val type_operation: String,
    val number1: Int,
    val number2: Int,
    val correct_answer: Int,
    val answer: Int
)