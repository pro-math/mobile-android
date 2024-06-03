package com.example.promath.utils

data class DifficultyModel(
    val value: String,
    val type: DifficultyType
)

enum class DifficultyType {
    TENS,
    HUNDREDS,
    THOUSANDS
}