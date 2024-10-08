package com.plcoding.translator_kmm.android.voice_to_text.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.plcoding.translator_kmm.android.MyApplicationTheme
import com.plcoding.translator_kmm.android.translate.presentation.components.gradientSurface
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun VoiceRecorderDisplay(
    modifier: Modifier,
    powerRatios: List<Float>
) {
    val primary = MaterialTheme.colors.primary
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
            .drawBehind {
                val powerRatioWidth = 3.dp.toPx()
                val powerRatioCount = (size.width / (2 * powerRatioWidth)).toInt()

                clipRect(
                    left = 0.0f,
                    top = 0.0f,
                    right = size.width,
                    bottom = size.height,
                ) {
                    powerRatios
                        .takeLast(powerRatioCount)
                        .reversed()
                        .forEachIndexed { i, ratio ->
                            val yTopStart = center.y - (size.height / 2f) * ratio
                            drawRoundRect(
                                color = primary,
                                topLeft = Offset(
                                    x = size.width - i * 2 * powerRatioWidth,
                                    y = yTopStart
                                ),
                                size = Size(
                                    width = powerRatioWidth,
                                    height = (center.y - yTopStart) * 2f
                                ),
                                cornerRadius = CornerRadius(100f)
                            )
                        }
                }
            }
    )
}

@Preview
@Composable
private fun VoiceRecorderDisplayPreview() {
    MyApplicationTheme {
        VoiceRecorderDisplay(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            powerRatios = (0..100).map {
                Random.nextFloat()
//                val percent = it / 100f
//                sin(percent * 2 * PI).toFloat()
            }
        )
    }
}