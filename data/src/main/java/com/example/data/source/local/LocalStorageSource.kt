package com.example.data.source.local

import android.content.Context
import com.example.domain.models.ResultModel

class LocalStorageSource(
    private val context: Context
) {

    fun getTokenFromLocalStorage(): ResultModel<String> {
        val token: String = context.getSharedPreferences("authToken", Context.MODE_PRIVATE).getString("token", "").toString()
        return if(token != "" && token != "null") {
            ResultModel.success(token)
        } else {
            ResultModel.failure(message = "Token not found")
        }
    }

    fun setTokenToLocalStorage(token: String) {
        context.getSharedPreferences("authToken", Context.MODE_PRIVATE).edit().putString("token", token).apply()
    }

    fun getCurrentTheme(): ResultModel<String> {
        val theme: String = context.getSharedPreferences("theme", Context.MODE_PRIVATE).getString("theme", "dark").toString()
        return if (theme != "" && theme != "null") {
            ResultModel.success(theme)
        } else {
            ResultModel.failure(theme)
        }
    }

}