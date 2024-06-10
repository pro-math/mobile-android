package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ProgressApiModel(
    val date: String,
    val stats: Float
)