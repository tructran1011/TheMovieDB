package com.me.themoviedb.common

data class Result<T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {
        fun <T> success(data: T?) = Result(Status.Success, data, null)
        fun <T> loading(data: T? = null) =
            Result(Status.Loading, data, null)
        fun <T> error(throwable: Throwable?, data: T? = null) =
            Result(Status.Error, data, throwable)
    }
}

fun Result<*>.isSuccess() = status == Status.Success

fun Result<*>.isLoading() = status == Status.Loading

fun Result<*>.isError() = status == Status.Error

sealed class Status {
    object Loading : Status()
    object Success : Status()
    object Error : Status()
}