package com.example.domain.models

data class RatingElementModel(
    val gameMode: GameMode,
    val duration: Int,
    val mathOperations: List<String>,
    val examplesCategory: Int,
    val username: String,
    val totalCount: Int,
    val correctCount: Int,
    val userId: Int,
    val gameSessionId: Int,
    val createdAt: String,
)