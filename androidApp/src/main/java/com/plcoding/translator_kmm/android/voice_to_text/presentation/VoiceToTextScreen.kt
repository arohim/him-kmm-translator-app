@file:OptIn(ExperimentalAnimationApi::class)

package com.plcoding.translator_kmm.android.voice_to_text.presentation

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.plcoding.translator_kmm.android.R
import com.plcoding.translator_kmm.android.core.theme.LightBlue
import com.plcoding.translator_kmm.voice_to_text.presentation.DisplayState
import com.plcoding.translator_kmm.voice_to_text.presentation.VoiceToTextEvent
import com.plcoding.translator_kmm.voice_to_text.presentation.VoiceToTextState

@Composable
fun VoiceToTextScreen(
    state: VoiceToTextState,
    languageCode: String,
    onResult: (String) -> Unit,
    onEvent: (VoiceToTextEvent) -> Unit
) {
    val context = LocalContext.current
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onEvent(
                VoiceToTextEvent.PermissionResult(
                    isGranted = isGranted,
                    isPermanentlyDeclined = !isGranted
                            && !shouldShowRequestPermissionRationale(
                        context as ComponentActivity,
                        Manifest.permission.RECORD_AUDIO
                    )
                )
            )
        }
    )
    LaunchedEffect(recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .size(75.dp),
                    onClick = {
                        if (state.displayState != DisplayState.DisplayingResults) {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        } else {
                            onResult(state.spokenText ?: "")
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                ) {
                    AnimatedContent(targetState = state.displayState) { displayState ->
                        when (displayState) {
                            DisplayState.Speaking -> {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = stringResource(id = R.string.record_audio),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            DisplayState.DisplayingResults -> {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = stringResource(id = R.string.apply),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            else -> {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                                    contentDescription = stringResource(id = R.string.record_audio),
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }
                if (state.displayState == DisplayState.DisplayingResults) {
                    IconButton(onClick = {
                        onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = stringResource(id = R.string.record_again),
                            tint = LightBlue
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        onEvent(VoiceToTextEvent.Close)
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
                if (state.displayState == DisplayState.Speaking) {
                    Text(
                        text = stringResource(id = R.string.listening),
                        color = LightBlue,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(targetState = state.displayState) { displayState ->
                    when (displayState) {
                        DisplayState.WaitingToTalk -> {
                            Text(
                                text = stringResource(id = R.string.start_talking),
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.Speaking -> {
                            VoiceRecorderDisplay(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                powerRatios = state.powerRatios
                            )
                        }

                        DisplayState.DisplayingResults -> {
                            Text(
                                text = state.spokenText ?: "",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.Error -> Text(
                            text = state.recordError ?: "Unknown error",
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.error
                        )

                        else -> Unit
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun VoiceToTextScreenPreview() {
    VoiceToTextScreen(
        state = VoiceToTextState(
            powerRatios = (0..100).map { it / 100f },
            spokenText = "Hello World!",
            displayState = DisplayState.WaitingToTalk,
        ),
        languageCode = "en",
        onResult = {},
        onEvent = { _ ->
        }
    )
}