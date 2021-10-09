package com.me.themoviedb.presentation.error

import retrofit2.HttpException
import java.io.IOException

sealed class AppError {

    object Factory {
        fun create(throwable: Throwable?): AppError {
            return when {
                throwable == null -> UnknownError

                isServerError(throwable) -> ServerError.fromThrowable(throwable)

                isNetworkError(throwable) -> NetworkError

                else -> UnknownError
            }
        }

        private fun isNetworkError(throwable: Throwable): Boolean {
            return throwable is IOException
        }

        private fun isServerError(throwable: Throwable): Boolean {
            return throwable is HttpException
        }
    }

    object UnknownError : AppError()

    object NetworkError : AppError()

    data class ServerError(val errorCode: Int?, val cause: Throwable?) : AppError() {

        companion object {

            fun fromThrowable(throwable: Throwable): ServerError {
                return if (throwable is HttpException) {
                    val errorCode = throwable.code()
                    ServerError(errorCode, throwable)
                } else {
                    ServerError(errorCode = null, cause = throwable)
                }
            }
        }
    }
}