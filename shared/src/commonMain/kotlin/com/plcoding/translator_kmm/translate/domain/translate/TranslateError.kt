package com.plcoding.translator_kmm.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TransactionException(error: TranslateError) : RuntimeException(
    "An error occur when translate $error"
)