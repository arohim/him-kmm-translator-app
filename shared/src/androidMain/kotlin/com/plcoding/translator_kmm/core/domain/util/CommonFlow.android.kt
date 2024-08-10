package com.plcoding.translator_kmm.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

actual open class CommonFlow<T> actual constructor(flow: Flow<T>) : Flow<T> by flow