package com.example.data.models

import com.example.domain.models.UserModel
import kotlinx.serialization.Serializable

@Serializable
class UserApiModel(
    val id: Int,
    val username: String
) {

    fun convertToUserModel(): UserModel = UserModel(
        id = id,
        login = username
    )

}