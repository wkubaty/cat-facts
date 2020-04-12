package com.kubaty.catfacts.util

data class DataState<T>(
    val message: Event<String>? = null,
    val loading: Boolean = false,
    val data: Event<T>? = null
) {
    companion object {
        fun <T> data(message: String? = null, data: T?): DataState<T> {
            return DataState(message = Event.messageEvent(message), data = Event.dataEvent(data))
        }

        fun <T> loading(loading: Boolean): DataState<T> {
            return DataState(loading = loading)
        }

        fun <T> error(message: String): DataState<T> {
            return DataState(message = Event.messageEvent(message))
        }
    }
}