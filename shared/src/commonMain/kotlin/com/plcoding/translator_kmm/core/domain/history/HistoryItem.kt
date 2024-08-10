package com.plcoding.translator_kmm.core.domain.history

data class HistoryItem(
    val id: Long?,
    val fromText: String,
    val toText: String,
    val fromLanguageCode: String,
    val toLanguageCode: String
)
