package com.kubaty.catfacts.util

data class DataState<T>(
    val message: String? = null,
    val loading: Boolean = false,
    val data: T? = null
) {
    companion object {
        fun <T> data(message: String? = null, data: T?): DataState<T> {
            return DataState(message = message, data = data)
        }

        fun <T> loading(loading: Boolean): DataState<T> {
            return DataState(loading = loading)
        }

        fun <T> error(message: String): DataState<T> {
            return DataState(message = message)
        }
    }
}