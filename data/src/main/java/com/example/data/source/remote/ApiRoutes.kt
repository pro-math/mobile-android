package com.example.data.source.remote

object ApiRoutes {

    private const val BASE_URL = "https://0331-94-25-191-98.ngrok-free.app/api/v1"
    const val USERS = "$BASE_URL/users"
    const val USER_ME = "$USERS/me/"
    const val USER_REGISTRATION = "$USERS/register"
    const val USER_LOGIN = "$USERS/token"
    const val GAME_SESSIONS = "$BASE_URL/game_sessions/"
    const val RATING = "$BASE_URL/ratings/"
    const val HISTORY = "$BASE_URL/ratings/me/"
    const val CHART = "$BASE_URL/chart/me/"
    const val ACHIEVEMENT = "$BASE_URL/achievements/"
    const val USER_ACHIEVEMENT = "$BASE_URL/achievements/me/"

}