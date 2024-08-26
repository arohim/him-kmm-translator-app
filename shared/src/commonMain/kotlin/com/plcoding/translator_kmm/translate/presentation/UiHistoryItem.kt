package com.plcoding.translator_kmm.translate.presentation

import com.plcoding.translator_kmm.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UiHistoryItem

        if (id != other.id) return false
        if (fromText != other.fromText) return false
        if (toText != other.toText) return false
        if (fromLanguage.language != other.fromLanguage.language) return false
        if (toLanguage.language != other.toLanguage.language) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + fromText.hashCode()
        result = 31 * result + toText.hashCode()
        result = 31 * result + fromLanguage.language.hashCode()
        result = 31 * result + toLanguage.language.hashCode()
        return result
    }
}
