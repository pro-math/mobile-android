package com.example.domain.models

data class UserHistoryElementModel(
    val gameMode: GameMode,
    val duration: Int,
    val mathOperations: List<String>,
    val examplesCategory: Int,
    val totalCount: Int,
    val correctCount: Int,
    val createdAt: String,
    val examples: List<ExampleModel>
)
