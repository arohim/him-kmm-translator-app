package com.plcoding.translator_kmm.history.data

import com.plcoding.translator_kmm.core.domain.history.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = id,
        fromText = fromText,
        toText = toText,
        fromLanguageCode = fromLanguageCode,
        toLanguageCode = toLanguageCode
    )
}
