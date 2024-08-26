package com.plcoding.translator_kmm.data.local

import com.plcoding.translator_kmm.core.domain.history.HistoryDataSource
import com.plcoding.translator_kmm.core.domain.history.HistoryItem
import com.plcoding.translator_kmm.core.domain.util.CommonFlow
import com.plcoding.translator_kmm.core.domain.util.toCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource : HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }
}