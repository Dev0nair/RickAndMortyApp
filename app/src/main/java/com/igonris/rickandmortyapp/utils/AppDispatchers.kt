package com.igonris.rickandmortyapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {
    var io: CoroutineDispatcher = Dispatchers.IO
    var main: CoroutineDispatcher = Dispatchers.Main
    var default: CoroutineDispatcher = Dispatchers.Default
}