package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val access_token: String,
    val token_type: String
)