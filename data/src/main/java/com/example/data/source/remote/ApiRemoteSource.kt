package com.example.data.source.remote

class ApiRemoteSource {

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
    ): Result<String> {
        TODO("REGISTRATION USER")

        return loginUser(login = login, password = password)
    }

}