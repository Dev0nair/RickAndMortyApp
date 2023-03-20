package com.igonris.rickandmortyapp.utils

import androidx.navigation.NavController
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import java.lang.Exception

fun NavController.goToCharacterDetail(simpleCharacter: SimpleCharacter) {
    navigate("details/${simpleCharacter.id}")
}

suspend fun <T, R> runAndProcess( action: suspend () -> T, parse: (T) -> R): Event<R> {
    return try {
        return  Event.Result(value = parse(action()))
    } catch (e: Exception) {
        Event.Error(message = e.message ?: "")
    }
}

fun <T> processEvent(event: Event<T>, onError: (String) -> Unit = {}, orElse: T): T {
    when (event) {
        is Event.Result -> return event.value
        is Event.Error -> onError(event.message)
        else -> return orElse
    }

    return orElse
}