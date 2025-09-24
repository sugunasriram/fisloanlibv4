package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightishGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400

@Composable
fun CenterProgress(modifier: Modifier = Modifier, top: Dp = 0.dp) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = top),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = appOrange,
            modifier = Modifier
                .padding(all = 8.dp)
                .size(30.dp)
        )
    }
}

@Composable
fun CenterProgressFixedHeight(modifier: Modifier = Modifier, top: Dp = 10.dp, size: Dp = 30.dp) {
    Column(
        modifier = modifier.padding(top = top).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = appOrange,
            modifier = Modifier
                .padding(all = 8.dp)
                .size(size)
        )
    }
}

@Composable
fun PagerIndicator(currentPage: Int, pageCount: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(if (currentPage == index) backgroundOrange else Color.LightGray)
                    .then(
                        if (currentPage == index) Modifier.width(24.dp) else Modifier.size(8.dp)
                    )
            )
        }
    }
}

@Composable
fun LoanStatusTracker(
    stepId: Int,
    bankItem: String = "Add Bank"
) {
    val allSteps = listOf(
        "Basic" to "Details",
        "Give consent" to "for bank data",
        "Select a" to "loan offer",
        "Complete KYC" to "Verification",
        bankItem to "Details",
        "Setup" to "Repayment"
    )

    val visibleSteps = 3

    // Show only 3 steps based on current progress
    val startIndex = when {
        stepId <= 2 -> 0
        stepId in 3..4 -> 1
        else -> 3
    }
    val displayedSteps = allSteps.subList(startIndex, startIndex + visibleSteps)
    val percentage = when (stepId) {
        1 -> 1f
        2 -> 12f
        3 -> 45f
        4 -> 59f
        5 -> 73f
        6 -> 86f
        else -> 0f
    }

    val iconResources = List(visibleSteps) { index ->
        val globalIndex = startIndex + index
        when {
            globalIndex + 1 < stepId -> R.drawable.tracker_done // Completed steps
            globalIndex + 1 == stepId -> R.drawable.tracker_in_progress // Current step
            else -> R.drawable.tracker_in_pending // Pending steps
        }
    }

    val colors = List(visibleSteps) { index ->
        val globalIndex = startIndex + index
        when {
            globalIndex + 1 < stepId -> appGreen // Completed steps
            globalIndex + 1 == stepId -> appOrange // Current step
            else -> lightishGrayColor // Pending steps
        }
    }

    HeaderCard(
        start = 15.dp, end = 15.dp, top = 10.dp, bottom = 0.dp,
        bottomStart = 5.dp, bottomEnd = 5.dp, topStart = 5.dp, topEnd = 5.dp, cardColor = appWhite
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                CircularProgressBar(
                    percentage = percentage / 100f, // Convert to 0.0 - 1.0 range
                    number = percentage.toInt(),
                    color = appGreen
                )
                Track(
                    items = visibleSteps,
                    showLineBeforeFirst = false,
                    extendLineAfterLast = stepId < 6,
                    brush = { index -> SolidColor(if (startIndex + index < stepId - 1) appGreen else lightishGrayColor) },
                    lineWidth = 2.dp,
                    pathEffect = { index -> if (startIndex + index == stepId - 2) PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) else null },
                    icon = { index -> Image(painter = painterResource(iconResources[index]), contentDescription = "Icon $index", modifier = Modifier.size(24.dp)) },
                    label = { index -> displayedSteps[index] },
                    color = { index -> colors[index] }
                )
            }
        }
    }
}

@Composable
fun Track(
    items: Int,
    brush: (from: Int) -> Brush,
    modifier: Modifier = Modifier,
    lineWidth: Dp = 1.dp,
    pathEffect: ((from: Int) -> PathEffect?)? = null,
    icon: @Composable (index: Int) -> Unit,
    color: @Composable (index: Int) -> Color,
    extendLineAfterLast: Boolean = true,
    showLineBeforeFirst: Boolean = false,
    label: @Composable (index: Int) -> Pair<String, String>
) {
    Box(
        modifier = modifier.padding(bottom = 0.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 12.dp)
                .zIndex(-1f)
        ) {
            val width = drawContext.size.width
            val height = drawContext.size.height
            val yOffset = height / 2
            val itemWidth = width / items

            var startOffset = itemWidth / 2
            var endOffset = startOffset

            val barWidth = lineWidth.toPx()

            // Draw line before the first index
            if (showLineBeforeFirst) {
                drawLine(
                    brush = brush.invoke(0),
                    start = Offset(0f, yOffset),
                    end = Offset(startOffset, yOffset),
                    strokeWidth = barWidth
                )
            }

            repeat(items - 1) {
                endOffset += itemWidth
                drawLine(
                    brush = brush.invoke(it),
                    start = Offset(startOffset, yOffset),
                    end = Offset(endOffset, yOffset),
                    strokeWidth = barWidth,
                    pathEffect = pathEffect?.invoke(it)
                )
                startOffset = endOffset
            }

            if (extendLineAfterLast) {
                drawLine(
                    brush = brush.invoke(items - 1),
                    start = Offset(endOffset, yOffset),
                    end = Offset(width, yOffset),
                    strokeWidth = barWidth,
                    pathEffect = null
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            repeat(items) { index ->
                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    icon.invoke(index)
                    Text(
                        text = label(index).first,
                        modifier = Modifier.padding(top = 30.dp),
                        style = normal12Text400,
                        color = color(index)
                    )
                    Text(
                        text = label(index).second,
                        modifier = Modifier.padding(top = 45.dp),
                        style = normal12Text400,
                        color = color(index)
                    )
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    color: Color = Color.Green,
    backgroundColor: Color = Color.LightGray,
    strokeWidth: Dp = 4.dp,
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 12.sp,
    radius: Dp = 20.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    horizontalPadding: Dp = 10.dp,
    verticalPadding: Dp = 10.dp
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = true,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(currentPercentage.value * 100).toInt()}%",
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
