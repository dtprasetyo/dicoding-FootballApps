package com.odora.id.footballapps

import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}