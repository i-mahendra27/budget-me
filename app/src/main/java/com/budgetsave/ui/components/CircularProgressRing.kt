package com.budgetsave.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Size variants for CircularProgressRing
 */
enum class RingSize {
    Small,
    Large
}

/**
 * Circular progress ring composable with animation support.
 *
 * @param progress Progress value between 0f and 1f
 * @param size Ring size variant (Small: 48dp/4dp stroke, Large: 120dp/8dp stroke)
 * @param modifier Modifier for the composable
 */
@Composable
fun CircularProgressRing(
    progress: Float,
    size: RingSize = RingSize.Small,
    modifier: Modifier = Modifier
) {
    val diameter: Dp = when (size) {
        RingSize.Small -> 48.dp
        RingSize.Large -> 120.dp
    }

    val strokeWidth: Float = when (size) {
        RingSize.Small -> 4f
        RingSize.Large -> 8f
    }

    CircularProgressRing(
        progress = progress,
        diameter = diameter,
        strokeWidth = strokeWidth,
        modifier = modifier
    )
}

/**
 * Customizable circular progress ring composable with animation.
 *
 * @param progress Progress value between 0f and 1f
 * @param diameter Diameter of the ring
 * @param strokeWidth Stroke width of the ring
 * @param modifier Modifier for the composable
 */
@Composable
fun CircularProgressRing(
    progress: Float,
    diameter: Dp,
    strokeWidth: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Canvas(modifier = modifier) {
        val sweepAngle = animatedProgress * 360f
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // Background circle - SurfaceVariant
        drawArc(
            color = androidx.compose.ui.graphics.Color(0xFFE0E0E0), // SurfaceVariant approximation
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )

        // Progress arc - Primary color
        drawArc(
            color = androidx.compose.ui.graphics.Color(0xFF2E7D32), // Primary approximation
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}
