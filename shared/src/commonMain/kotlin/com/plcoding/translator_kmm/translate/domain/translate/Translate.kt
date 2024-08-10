package com.plcoding.translator_kmm.translate.domain.translate

import com.plcoding.translator_kmm.core.domain.history.HistoryDataSource
import com.plcoding.translator_kmm.core.domain.history.HistoryItem
import com.plcoding.translator_kmm.core.domain.langauge.Language
import com.plcoding.translator_kmm.core.domain.util.Resource

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {
    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(fromLanguage, fromText, toLanguage)
            historyDataSource.insertHistoryItem(
                item = HistoryItem(
                    id = null,
                    fromText = fromText,
                    toText = translatedText,
                    fromLanguageCode = fromLanguage.langCode,
                    toLanguageCode = toLanguage.langCode
                )
            )
            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}