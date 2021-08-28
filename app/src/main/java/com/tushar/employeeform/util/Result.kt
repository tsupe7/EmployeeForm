package com.tushar.employeeform.util

/**
 * Created by Tushar on 28/8/21.
 */
data class Result<out T> private constructor(val status: Status, val data: T?) {

    companion object {
        fun <T> success(data: T? = null): Result<T> =
            Result(Status.SUCCESS, data)

        fun <T> error(data: T? = null): Result<T> =
            Result(Status.ERROR, data)

        fun <T> noInternet(data: T? = null): Result<T> =
            Result(Status.NO_INTERNET, data)

        fun <T> loading(data: T? = null): Result<T> =
            Result(Status.LOADING, data)

        fun <T> unknown(data: T? = null): Result<T> =
            Result(Status.UNKNOWN, data)
    }
}