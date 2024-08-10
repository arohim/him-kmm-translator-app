package com.plcoding.translator_kmm.translate.data.translate

import com.plcoding.translator_kmm.NetworkConstants
import com.plcoding.translator_kmm.core.domain.langauge.Language
import com.plcoding.translator_kmm.translate.domain.translate.TransactionException
import com.plcoding.translator_kmm.translate.domain.translate.TranslateClient
import com.plcoding.translator_kmm.translate.domain.translate.TranslateError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KTorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        val result = try {
            httpClient.post {
                NetworkConstants.BASE_URL + "/translate"
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TransactionException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw TransactionException(TranslateError.SERVER_ERROR)

            in 400..499 -> throw TransactionException(TranslateError.CLIENT_ERROR)
            else -> throw TransactionException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TransactionException(TranslateError.SERVER_ERROR)
        }
    }
}
