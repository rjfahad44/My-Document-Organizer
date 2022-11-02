package com.ft.my_document_organizer.state

sealed class NetworkState<T>(val data: T? = null, val message: String? = null) {
    class Empty<T> : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
    class Success<T>(data: T) : NetworkState<T>(data, null)

    @Suppress("UNUSED_PARAMETER")
    class Error<T>(message: String, data: T? = null) : NetworkState<T>(null, message)
}