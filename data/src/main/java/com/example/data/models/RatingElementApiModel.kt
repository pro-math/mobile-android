package com.example.data.models

import com.example.domain.models.GameMode
import com.example.domain.models.RatingElementModel
import kotlinx.serialization.Serializable

@Serializable
class RatingElementApiModel(
    val game_mode: String,
    val duration: Int,
    val math_operations: List<String>,
    val examples_category: Int,
    val username: String,
    val total_count: Int,
    val correct_count: Int,
    val user_id: Int,
    val game_session_id: Int,
    val created_at: String
) {

    fun toRatingElementModel(): RatingElementModel {
        return RatingElementModel(
            gameMode = when (game_mode) {
                "time_mode" -> GameMode.TimeMode
                "count_mode" -> GameMode.CountMode
                else -> GameMode.TimeMode
            },
            duration = duration,
            mathOperations = math_operations,
            examplesCategory = examples_category,
            username = username,
            totalCount = total_count,
            correctCount = correct_count,
            userId = user_id,
            gameSessionId = game_session_id,
            createdAt = created_at
        )
    }

}