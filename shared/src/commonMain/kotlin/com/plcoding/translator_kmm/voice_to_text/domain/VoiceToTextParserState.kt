package com.plcoding.translator_kmm.voice_to_text.domain

data class VoiceToTextParserState(
    val result: String? = null,
    val error: String? = null,
    val powerRatio: Float = 0f,
    val isSpeaking: Boolean = false
)
