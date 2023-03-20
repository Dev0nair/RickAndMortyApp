package com.igonris.rickandmortyapp.utils

sealed class Event<T> {
    class Empty<T>: Event<T>()
    class Loading<T>(loading: Boolean): Event<T>()
    class Result<T>(val value: T): Event<T>()
    class Error<T>(val message: String): Event<T>()
}
