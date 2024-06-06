package com.example.data.source.remote

object ApiRoutes {

    private const val BASE_URL = "https://4368-83-171-69-39.ngrok-free.app/api/v1"
    const val USERS = "$BASE_URL/users"
    const val USER_ME = "$USERS/me"
    const val USER_REGISTRATION = "$USERS/register"
    const val USER_LOGIN = "$USERS/token"
    const val GAME_SESSIONS = "$BASE_URL/game_sessions/"

}