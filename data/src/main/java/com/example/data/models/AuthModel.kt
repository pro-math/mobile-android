package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    val username: String,
    val password: String
)
