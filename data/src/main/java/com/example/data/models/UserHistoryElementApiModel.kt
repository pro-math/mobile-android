package com.example.data.models

import com.example.domain.models.ExampleModel
import com.example.domain.models.GameMode
import com.example.domain.models.UserHistoryElementModel
import kotlinx.serialization.Serializable

@Serializable
class UserHistoryElementApiModel(
    val game_mode: String,
    val duration: Int,
    val math_operations: List<String>,
    val examples_category: Int,
    val total_count: Int,
    val correct_count: Int,
    val created_at: String,
    val examples: List<ExampleApiModel>
) {

    fun toUserHistoryElementModel() = UserHistoryElementModel(
        gameMode = if (game_mode == "time_mode") {
            GameMode.TimeMode
        } else {
            GameMode.CountMode
        },
        duration = duration,
        mathOperations = math_operations,
        examplesCategory = examples_category,
        totalCount = total_count,
        correctCount = correct_count,
        createdAt = created_at,
        examples = buildList {
            examples.forEach {
                add(ExampleModel(
                    typeOperation = it.type_operation,
                    number1 = it.number1,
                    number2 = it.number2,
                    correctAnswer = it.correct_answer,
                    answer = it.user_answer
                ))
            }
        }
    )

}