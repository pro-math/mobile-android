package com.example.data.source.remote

import android.util.Log
import com.example.data.models.AuthModel
import com.example.data.models.DetailModel
import com.example.data.models.ExampleApiModel
import com.example.data.models.GameSessionModel
import com.example.data.models.LoginModel
import com.example.data.models.UserApiModel
import com.example.domain.models.ExampleModel
import com.example.domain.models.GameMode
import com.example.domain.models.GameSession
import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.lang.Exception

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
                        answer = it.answer
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
        examples = "[" + examples.substring(startIndex = 0, endIndex = examples.length - 2) + "]"

        var listOperations: String = ""
        gameSession.mathOperations.forEach {
            listOperations += """"$it", """
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
                    "duration": 0,
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

}