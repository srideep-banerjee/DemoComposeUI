package com.example.blackofferassignment.view.refine

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceSelector(start: Float = 1f, end: Float = 100f) {
    Column {
        var progress by remember {
            mutableFloatStateOf(50f)
        }
        val primaryColor = MaterialTheme.colorScheme.primary

        Text(
            text = "Select Hyper Local Distance",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))

        Layout(
            content = {
                Text(
                    text = progress.toInt().toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp)
                )
                Canvas(
                    modifier = Modifier
                        .width(10.dp)
                        .height(6.dp),
                    onDraw = {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val path = Path()
                        path.moveTo(0f, 0f)
                        path.lineTo(canvasWidth, 0f)
                        path.lineTo(canvasWidth / 2f, canvasHeight)
                        drawPath(path = path, color = primaryColor)
                    }
                )
            },
            measurePolicy = {measurables, constraints ->
                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
                }
                val progressOffsetX = (progress - start) / (end - start) * constraints.maxWidth
                val textOffsetX = progressOffsetX - (placeables[0].width / 2)
                val cappedTextOffsetX =
                    if (textOffsetX + placeables[0].width > constraints.maxWidth + placeables[1].width)
                        constraints.maxWidth - placeables[0].width + placeables[1].width
                    else
                        textOffsetX.roundToInt()
                val pointerOffsetX = progressOffsetX - placeables[1].width / 2
                val pointerOffsetY = placeables[0].height
                val totalHeight = placeables[0].height + placeables[1].height
                return@Layout layout(width = constraints.maxWidth, height = totalHeight) {
                    placeables[0].place(
                        x = cappedTextOffsetX,
                        y = 0
                    )
                    placeables[1].place(
                        x = pointerOffsetX.roundToInt(),
                        y = pointerOffsetY
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

        val interactionSource = remember {
            MutableInteractionSource()
        }
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false,
            LocalRippleTheme provides NoRippleTheme
        ) {
            Slider(
                value = progress,
                onValueChange = { progress = it },
                valueRange = start..end,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .indication(
                        interactionSource = interactionSource,
                        indication = null
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "1 Km",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "100 Km",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}