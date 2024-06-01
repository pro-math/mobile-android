package com.example.domain.models


class ResultModel<out T> (
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        FAILURE
    }

    companion object {

        fun <T> success(data: T): ResultModel<T> = ResultModel(Status.SUCCESS, data, null)

        fun <T> failure(message: String?, data: T? = null): ResultModel<T> = ResultModel(Status.FAILURE, data, message)

    }

}