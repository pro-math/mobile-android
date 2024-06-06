package com.example.domain.models

data class GameSession(
    val gameMode: GameMode,
    val duration: Long,
    val mathOperations: List<String>,
    val examplesCategory: Int,
    val examples: List<ExampleModel>,
    val totalCount: Int,
    val correctCount: Int
)

data class ExampleModel(
    val typeOperation: String,
    val number1: Int,
    val number2: Int,
    val correctAnswer: Int,
    val answer: Int
)