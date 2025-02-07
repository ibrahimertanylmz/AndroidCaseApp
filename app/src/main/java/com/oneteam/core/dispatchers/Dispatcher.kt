package com.oneteam.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {

    val main: CoroutineDispatcher

    val io: CoroutineDispatcher

    val db: CoroutineDispatcher

    val computation: CoroutineDispatcher

}