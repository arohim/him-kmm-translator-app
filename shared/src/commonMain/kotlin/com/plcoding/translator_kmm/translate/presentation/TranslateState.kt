package com.plcoding.translator_kmm.translate.presentation

import com.plcoding.translator_kmm.core.presentation.UiLanguage
import com.plcoding.translator_kmm.translate.domain.translate.TranslateError

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("de"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TranslateState

        if (fromText != other.fromText) return false
        if (toText != other.toText) return false
        if (isTranslating != other.isTranslating) return false
        if (fromLanguage.language != other.fromLanguage.language) return false
        if (toLanguage.language != other.toLanguage.language) return false
        if (isChoosingFromLanguage != other.isChoosingFromLanguage) return false
        if (isChoosingToLanguage != other.isChoosingToLanguage) return false
        if (error != other.error) return false
        if (history != other.history) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fromText.hashCode()
        result = 31 * result + (toText?.hashCode() ?: 0)
        result = 31 * result + isTranslating.hashCode()
        result = 31 * result + fromLanguage.language.hashCode()
        result = 31 * result + toLanguage.language.hashCode()
        result = 31 * result + isChoosingFromLanguage.hashCode()
        result = 31 * result + isChoosingToLanguage.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + history.hashCode()
        return result
    }
}
