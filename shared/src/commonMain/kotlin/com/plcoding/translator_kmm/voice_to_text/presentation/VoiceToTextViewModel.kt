package com.plcoding.translator_kmm.voice_to_text.presentation

import com.plcoding.translator_kmm.core.domain.util.toCommonStateFlow
import com.plcoding.translator_kmm.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val parser: VoiceToTextParser,
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())
    val state = _state.combine(parser.state) { state, voiceResult ->
        state.copy(
            spokenText = voiceResult.result,
            recordError = if (state.canRecord) {
                voiceResult.error
            } else {
                "Can't record audio without permission"
            },
            displayState = when {
                !state.canRecord || voiceResult.error != null -> DisplayState.Error
                voiceResult.result?.isNotBlank() == true && !voiceResult.isSpeaking -> {
                    DisplayState.DisplayingResults
                }

                voiceResult.isSpeaking -> DisplayState.Speaking
                else -> DisplayState.WaitingToTalk
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        VoiceToTextState()
    ).toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.Speaking) {
                    _state.update {
                        it.copy(
                            powerRatios = it.powerRatios + parser.state.value.powerRatio
                        )
                    }
                }
                delay(50L)
            }
        }
    }

    fun onEvent(event: VoiceToTextEvent) {
        when (event) {
            is VoiceToTextEvent.PermissionResult -> {
                _state.update { it.copy(canRecord = event.isGranted) }
            }

            is VoiceToTextEvent.Reset -> {
                parser.reset()
                _state.update { VoiceToTextState() }
            }

            is VoiceToTextEvent.ToggleRecording -> toggleListening(event.languageCode)
            else -> Unit
        }
    }

    private fun toggleListening(languageCode: String) {
        _state.update { it.copy(powerRatios = emptyList()) }
        parser.cancel()
        if (state.value.displayState == DisplayState.Speaking) {
            parser.stopListening()
        } else {
            parser.startListening(languageCode)
        }
    }
}