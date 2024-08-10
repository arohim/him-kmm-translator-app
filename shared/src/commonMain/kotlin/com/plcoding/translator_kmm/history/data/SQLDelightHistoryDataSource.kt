package com.plcoding.translator_kmm.history.data

import com.plcoding.translator_kmm.core.domain.history.HistoryDataSource
import com.plcoding.translator_kmm.core.domain.history.HistoryItem
import com.plcoding.translator_kmm.core.domain.util.CommonFlow
import com.plcoding.translator_kmm.core.domain.util.toCommonFlow
import com.plcoding.translator_kmm.database.TranslateDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SQLDelightHistoryDataSource constructor(
    private val db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { historyEntity ->
                historyEntity.map { it.toHistoryItem() }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        return queries
            .insertHistoryEntity(
                id = item.id,
                fromLanguageCode = item.fromLanguageCode,
                fromText = item.fromText,
                toLanguageCode = item.toLanguageCode,
                toText = item.toText,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
    }
}
