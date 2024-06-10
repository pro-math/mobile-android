package com.example.data.models

import com.example.domain.models.AchievementModel
import kotlinx.serialization.Serializable

@Serializable
class AchievementApiModel(
    val name: String,
    val description: String,
    val image: String,
    val id: Int
) {

    fun toAchievementModel(): AchievementModel = AchievementModel(
        name = name,
        description = description,
        image = image,
        id = id
    )

}