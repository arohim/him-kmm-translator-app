package com.plcoding.translator_kmm.voice_to_text.domain

import com.plcoding.translator_kmm.core.domain.util.CommonStateFlow

data class VoiceToTextParserState(
    val result: String? = null,
    val error: String? = null,
    val powerRatio: Float = 0f,
    val isSpeaking: Boolean = false
)
