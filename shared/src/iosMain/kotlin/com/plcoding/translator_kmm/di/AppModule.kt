package com.plcoding.translator_kmm.di

import com.plcoding.translator_kmm.core.domain.history.HistoryDataSource
import com.plcoding.translator_kmm.database.TranslateDatabase
import com.plcoding.translator_kmm.history.data.SQLDelightHistoryDataSource
import com.plcoding.translator_kmm.history.data.local.DatabaseDriverFactory
import com.plcoding.translator_kmm.translate.data.remote.HttpClientFactory
import com.plcoding.translator_kmm.translate.data.translate.KTorTranslateClient
import com.plcoding.translator_kmm.translate.domain.translate.Translate
import com.plcoding.translator_kmm.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SQLDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().createDriver()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KTorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }
}