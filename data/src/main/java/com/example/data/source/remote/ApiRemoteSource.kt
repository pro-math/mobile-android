package com.example.data.source.remote

import android.util.Log
import com.example.data.models.AchievementApiModel
import com.example.data.models.DetailModel
import com.example.data.models.ExampleApiModel
import com.example.data.models.GameSessionModel
import com.example.data.models.LoginModel
import com.example.data.models.ProgressApiModel
import com.example.data.models.RatingElementApiModel
import com.example.data.models.UserApiModel
import com.example.data.models.UserHistoryElementApiModel
import com.example.domain.models.AchievementModel
import com.example.domain.models.GameMode
import com.example.domain.models.GameSession
import com.example.domain.models.ProgressModel
import com.example.domain.models.RatingElementModel
import com.example.domain.models.ResultModel
import com.example.domain.models.UserHistoryElementModel
import com.example.domain.models.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.json.JSONObject

class ApiRemoteSource() {

    private val httpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun loginUser(
        login: String,
        password: String
    ): ResultModel<String> {
        try {
            val response = httpClient.post(ApiRoutes.USER_LOGIN) {
                contentType(ContentType.Application.Json)
                setBody("""{"username":"$login", "password":"$password"}""")
            }
            if (response.status.value in 200..299) {
                val loginModel: LoginModel = response.body()
                return ResultModel.success(loginModel.access_token)
            } else if (response.status.value in 400..499) {
                val detailModel: DetailModel = response.body()
                return ResultModel.failure(message = detailModel.detail)
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun registrationUser(
        login: String,
        password: String
    ): ResultModel<String> {
        try {
            val response = httpClient.post {
                url(ApiRoutes.USER_REGISTRATION)
//                parameter(key = "username", value = login)
//                parameter(key = "password", value = password)
//                val json = Json {
//                    "username" to login
//                    "password" to password
//                }
//                header()
                val answer = JSONObject("""{"username":"$login", "password":"$password"}""")
                contentType(ContentType.Application.Json)
                setBody("""{"username":"$login", "password":"$password"}""")
            }
            if (response.status.value in 200..299) {
                return loginUser(login = login, password = password)
            } else if (response.status.value in 400..499) {
                val detailModel: DetailModel = response.body()
                return ResultModel.failure(message = detailModel.detail)
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun getUser(token: String): ResultModel<UserModel> {
        try {
            val response = httpClient.get {
                url(ApiRoutes.USER_ME)
                header(key = "Authorization", value = "Bearer $token")
            }

            if (response.status.value in 200..299) {
                val userApi: UserApiModel = response.body()
                return ResultModel.success(data = userApi.convertToUserModel())
            } else if (response.status.value in 400..499) {
                val detail: DetailModel = response.body()
                return ResultModel.failure(message = detail.detail)
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun postGameSession(gameSession: GameSession, token: String) {
        val gameSessionModel = GameSessionModel(
            game_mode = when (gameSession.gameMode) {
                GameMode.TimeMode -> "time_mode"
                GameMode.CountMode -> "count_mode"
            },
            duration = gameSession.duration,
            math_operations = gameSession.mathOperations,
            examples_category = gameSession.examplesCategory,
            examples = buildList {
                gameSession.examples.forEach {
                    add(
                        ExampleApiModel(
                        type_operation = it.typeOperation,
                        number1 = it.number1,
                        number2 = it.number2,
                        correct_answer = it.correctAnswer,
                        user_answer = it.answer
                    )
                    )
                }
            },
            total_count = gameSession.totalCount,
            correct_count = gameSession.correctCount
        )
        Log.i("TEST GAME SESSION", gameSessionModel.toString())

        var examples: String = ""
        gameSession.examples.forEach {
            val example = """{
                "type_operation": "${it.typeOperation}",
                "number1": ${it.number1},    
                "number2": ${it.number2},
                "correct_answer": ${it.correctAnswer},
                "user_answer": ${it.answer}
            }""".trimIndent()

            examples += "$example, "
        }
        if (examples == "") {
            examples = "[]"
        } else {
            examples = "[" + examples.substring(startIndex = 0, endIndex = examples.length - 2) + "]"
        }

        var listOperations: String = ""
        gameSession.mathOperations.forEach {
            listOperations += """"$it", """.trimIndent()
        }
        listOperations = "[" + listOperations.substring(startIndex = 0, endIndex = listOperations.length - 2) + "]"
        Log.i("TEST GAME SESSION", """{
                    "game_mode": "${when (gameSession.gameMode) {
            GameMode.TimeMode -> "time_mode"
            GameMode.CountMode -> "count_mode"
        }}",
                    "duration": 0,
                    "math_operations": ${listOperations},
                    "examples_category": ${gameSession.examplesCategory},
                    "examples": ${examples},
                    "total_count": ${gameSession.totalCount},
                    "correct_count": ${gameSession.correctCount}
                }""".trimIndent())

        try {
            val response = httpClient.post {
                url(ApiRoutes.GAME_SESSIONS)
                contentType(ContentType.Application.Json)
                setBody("""{
                    "game_mode": "${when (gameSession.gameMode) {
                    GameMode.TimeMode -> "time_mode"
                    GameMode.CountMode -> "count_mode"
                }}",
                    "duration": ${gameSession.duration},
                    "math_operations": ${listOperations},
                    "examples_category": ${gameSession.examplesCategory},
                    "examples": ${examples},
                    "total_count": ${gameSession.totalCount},
                    "correct_count": ${gameSession.correctCount}
                }""".trimIndent())
                header(key = "Authorization", value = "Bearer $token")
            }
            if (response.status.value in 200..299) {

            } else {
                Log.i("TEST GAME SESSION", response.body())
            }
        } catch (e: Exception) {
            Log.i("GAME SESSION CREATE", e.message.toString())
        }
    }

    suspend fun getListRating(
        gameMode: GameMode,
        examplesCategory: Int,
        mathOperations: List<String>,
        limit: Int,
        offset: Int,
        difficulty: Int
    ): ResultModel<List<RatingElementModel>> {
        try {
//            var mathOperationsConvert: String = ""
//            mathOperations.forEach {
//                mathOperationsConvert += """"$it", """.trimIndent()
//            }
//            mathOperationsConvert = "[" + mathOperationsConvert.substring(0, mathOperationsConvert.length - 2) + "]".trimIndent()
            val urlBuilder = URLBuilder(ApiRoutes.RATING).apply {
                parameters.append("game_mode", when (gameMode) {
                    GameMode.CountMode -> "count_mode"
                    GameMode.TimeMode -> "time_mode"
                })
                parameters.append("examples_category", examplesCategory.toString())
                mathOperations.forEach {
                    parameters.append("math_operations", it)
                }
                parameters.append("difficulty", difficulty.toString())
                parameters.append("limit", limit.toString())
                parameters.append("offset", offset.toString())
            }
//            Log.i("TEST RATING", mathOperationsConvert)
            val response = httpClient.get {
                url(urlBuilder.buildString())
//                parameter(key = "game_mode", value = when (gameMode) {
//                    GameMode.CountMode -> "count_mode"
//                    GameMode.TimeMode -> "time_mode"
//                })
//                parameter(key = "examples_category", value = examplesCategory)
//                parameter(key = "math_operations", value = mathOperationsConvert)
//                parameter(key = "limit", value = limit)
//                parameter(key = "offset", value = offset)
                contentType(ContentType.Application.Json)
            }

            if (response.status.value in 200..299) {
                val result: List<RatingElementApiModel> = response.body()
                val resultFinal: MutableList<RatingElementModel> = mutableListOf()
                for (i in result.indices) {
                    resultFinal.add(result[i].toRatingElementModel())
                }
                return ResultModel.success(data = resultFinal)
            } else if (response.status.value in 400..499) {
                val detail: String = response.body()
                return ResultModel.failure(message = detail)
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun getListUserRating(
        token: String,
        gameMode: GameMode,
        examplesCategory: Int,
        mathOperations: List<String>,
        limit: Int,
        offset: Int,
        difficulty: Int
    ): ResultModel<List<UserHistoryElementModel>> {
        try {
//            var mathOperationsConvert: String = ""
//            mathOperations.forEach {
//                mathOperationsConvert += """"$it", """.trimIndent()
//            }
//            mathOperationsConvert = "[" + mathOperationsConvert.substring(0, mathOperationsConvert.length - 2) + "]".trimIndent()
            val urlBuilder = URLBuilder(ApiRoutes.HISTORY).apply {
                parameters.append("game_mode", when (gameMode) {
                    GameMode.CountMode -> "count_mode"
                    GameMode.TimeMode -> "time_mode"
                })
                parameters.append("examples_category", examplesCategory.toString())
                mathOperations.forEach {
                    parameters.append("math_operations", it)
                }
                parameters.append("difficulty", difficulty.toString())
                parameters.append("limit", limit.toString())
                parameters.append("offset", offset.toString())
            }
//            Log.i("TEST RATING", mathOperationsConvert)
            val response = httpClient.get {
                url(urlBuilder.buildString())
                header(key = "Authorization", value = "Bearer $token")
//                parameter(key = "game_mode", value = when (gameMode) {
//                    GameMode.CountMode -> "count_mode"
//                    GameMode.TimeMode -> "time_mode"
//                })
//                parameter(key = "examples_category", value = examplesCategory)
//                parameter(key = "math_operations", value = mathOperationsConvert)
//                parameter(key = "limit", value = limit)
//                parameter(key = "offset", value = offset)
                contentType(ContentType.Application.Json)
            }

            if (response.status.value in 200..299) {
                val result: List<UserHistoryElementApiModel> = response.body()
                val resultFinal: MutableList<UserHistoryElementModel> = mutableListOf()
                for (i in result.indices) {
                    resultFinal.add(result[i].toUserHistoryElementModel())
                }
                return ResultModel.success(data = resultFinal)
            } else if (response.status.value in 400..499) {
                val detail: String = response.body()
                return ResultModel.failure(message = detail)
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun deleteUser(token: String) {
        try {
            val response = httpClient.delete {
                url(ApiRoutes.USER_ME)
                header(key = "Authorization", value = "Bearer $token")
            }
        } catch (e: Exception) {
//            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun getProgressUser(
        token: String,
        gameMode: GameMode,
        examplesCategory: Int,
        difficulty: Int,
        mathOperations: List<String>
    ): ResultModel<List<ProgressModel>> {
        try {
            val urlBuilder = URLBuilder(ApiRoutes.CHART).apply {
                parameters.append("game_mode", when (gameMode) {
                    GameMode.CountMode -> "count_mode"
                    GameMode.TimeMode -> "time_mode"
                })
                parameters.append("examples_category", examplesCategory.toString())
                mathOperations.forEach {
                    parameters.append("math_operations", it)
                }
                parameters.append("difficulty", difficulty.toString())
            }

            val response = httpClient.get {
                url(urlBuilder.buildString())
                header(key = "Authorization", value = "Bearer $token")
                contentType(ContentType.Application.Json)
            }
            Log.i("TEST PROGRESS", token)

            if (response.status.value in 200..299) {
                val result: List<ProgressApiModel> = response.body()
                val resultFinal: MutableList<ProgressModel> = mutableListOf()
                result.forEach {
                    resultFinal.add(ProgressModel(date = it.date, stats = it.stats))
                }
                Log.i("TEST PROGRESS REMOTE SOURCE GET", urlBuilder.buildString())
                Log.i("TEST PROGRESS REMOTE SOURCE GET", token)
                Log.i("TEST PROGRESS REMOTE SOURCE GET", response.body())

                return ResultModel.success(data = resultFinal)
            } else if (response.status.value in 400..499) {
                return ResultModel.failure(message = response.body())
            } else {
                return ResultModel.failure(message = "Server error")
            }

        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun getAllAchievement(): ResultModel<List<AchievementModel>> {
        try {
            val response = httpClient.get {
                url(ApiRoutes.ACHIEVEMENT)
            }

            if (response.status.value in 200..299) {
                val result: List<AchievementApiModel> = response.body()
                val resultFinal: List<AchievementModel> = buildList {
                    result.forEach {
                        add(it.toAchievementModel())
                    }
                }
                return ResultModel.success(data = resultFinal)
            } else if (response.status.value in 400..499) {
                return ResultModel.failure(message = response.body())
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

    suspend fun getUserAchievement(token: String): ResultModel<List<AchievementModel>> {
        try {
            val response = httpClient.get {
                url(ApiRoutes.USER_ACHIEVEMENT)
                header(key = "Authorization", value = "Bearer $token")
            }

            if (response.status.value in 200..299) {
                val result: List<AchievementApiModel> = response.body()
                val resultFinal: List<AchievementModel> = buildList {
                    result.forEach {
                        add(it.toAchievementModel())
                    }
                }
                return ResultModel.success(data = resultFinal)
            } else if (response.status.value in 400..499) {
                return ResultModel.failure(message = response.body())
            } else {
                return ResultModel.failure(message = "Server error")
            }
        } catch (e: Exception) {
            return ResultModel.failure(message = e.message)
        }
    }

}