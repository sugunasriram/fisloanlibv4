package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.greenCard
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightishGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.TableRow

@Composable
fun DisplayCard(
    modifier: Modifier = Modifier,
    cardColor: Color = backgroundOrange,
    borderColor: Color = appOrange,
    start: Dp = 20.dp,
    end: Dp = 20.dp,
    roundedCornerDp: Dp = 16.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(cardColor, RoundedCornerShape(roundedCornerDp))
            .border(1.dp, borderColor, RoundedCornerShape(roundedCornerDp))
    ) {
        content()
    }
}

@Composable
fun FullWidthRoundShapedCard(
    modifier: Modifier = Modifier,
    start: Dp = 15.dp,
    end: Dp = 15.dp,
    top: Dp = 15.dp,
    shapeSize: Dp = 12.dp,
    bottomPadding: Dp = 20.dp,
    cardColor: Color = greenCard,
    onClick: () -> Unit = {},
    clickable: Boolean = true,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    gradientColors: List<Color> = listOf(
        backgroundOrange,
        appOrange,
        Color(0xFFFF7043)
    ), // orange gradient
    bottom: Dp = 15.dp,
    content: @Composable () -> Unit
) {
    val gradient = Brush.linearGradient(colors = gradientColors)
    Column(
        horizontalAlignment = alignment,
        modifier = modifier
            .then(
                if (clickable) Modifier.clickable { onClick() }
                else Modifier
            )
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(
                shape = RoundedCornerShape(shapeSize),
                brush = gradient
            )
            .padding(bottomPadding)
    ) {
        content()
    }
}

@Composable
fun FullWidthRoundShapedElevatedCard(
    modifier: Modifier = Modifier,
    start: Dp = 15.dp,
    end: Dp = 15.dp,
    top: Dp = 15.dp,
    shapeSize: Dp = 12.dp,
    bottomPadding: Dp = 20.dp,
    onClick: () -> Unit,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    gradientColors: List<Color> = listOf(
        backgroundOrange,
        appOrange,
        Color(0xFFFF7043)
    ),
    color: Color = greenCard,
    bottom: Dp = 15.dp,
    content: @Composable () -> Unit
) {
    val gradient = Brush.linearGradient(colors = gradientColors)

    Box(
        modifier = modifier
            .padding(start = start, end = end, top = top, bottom = bottom)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(shapeSize),
            shadowElevation = 10.dp,
            tonalElevation = 10.dp, // for Material 3
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = gradient, shape = RoundedCornerShape(shapeSize))
        ) {
            Column(
                modifier = Modifier
                    .background(brush = gradient, shape = RoundedCornerShape(shapeSize))
                    .padding(bottomPadding),
                horizontalAlignment = alignment
            ) {
                content()
            }
        }
    }
}


@Preview
@Composable
fun FullWidthRoundShapedElevatedCardPreview(){
    FullWidthRoundShapedElevatedCard(
        onClick = { },
        start = 0.dp,
        end = 0.dp,
        top = 0.dp,
        bottom = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TableRow(
                emiNumber = "1",
                dueDate = "20-July-2024",
                amount = "88846.00",
                status = "NOT-PAID"
            )
            TableRow(
                emiNumber = "1",
                dueDate = "20-July-2024",
                amount = "88846.00",
                status = "NOT-PAID"
            )
        }
    }
}


@Composable
fun BorderCardWithElevation(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Transparent,
    backgroundColor: Color = appWhite,
    borderColor: Color = appOrange,
    start: Dp = 20.dp,
    end: Dp = 20.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom),
        elevation = 12.dp,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            content()
        }
    }
}

@Composable
fun ClickableLoanStatusCard(
    modifier: Modifier = Modifier,
    cardColor: Color = greenCard,
    borderColor: Color = lightGray,
    start: Dp = 20.dp,
    end: Dp = 20.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(
                shape = RoundedCornerShape(3.dp),
                color = cardColor
            )
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(5.dp))
    ) {
        content()
    }
}

@Composable
fun HeaderCard(
    modifier: Modifier = Modifier,
    cardColor: Color = appOrange,
    borderColor: Color = appOrange,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    bottomStart: Dp = 20.dp,
    bottomEnd: Dp = 20.dp,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(
                shape = RoundedCornerShape(
                    bottomStart = bottomStart,
                    bottomEnd = bottomEnd,
                    topStart = topStart,
                    topEnd = topEnd
                ),
                color = cardColor
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(
                    bottomStart = bottomStart,
                    bottomEnd = bottomEnd,
                    topStart = topStart,
                    topEnd = topEnd
                )
            )
    ) {
        content()
    }
}

@Preview
@Composable
fun FullWidthRoundShapedCardPreview() {
    FullWidthRoundShapedCard(
        onClick = { },
        cardColor = lightishGray,
        start = 0.dp,
        end = 0.dp,
        top = 0.dp,
        bottom = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TableRow(
                emiNumber = "1",
                dueDate = "20-July-2024",
                amount = "88846.00",
                status = "NOT-PAID"
            )
            TableRow(
                emiNumber = "1",
                dueDate = "20-July-2024",
                amount = "88846.00",
                status = "NOT-PAID"
            )
        }
    }
}

@Composable
fun DashedBorderCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    tintColor: Color = appOrange
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .clickable { onClick() }
            .drawBehind {
                val strokeWidth = 4f
                val dashWidth = 10f
                val dashGap = 10f
                val paint = Paint().asFrameworkPaint().apply {
                    style = android.graphics.Paint.Style.STROKE
                    color = tintColor.toArgb()
                    this.strokeWidth = strokeWidth
                    pathEffect = android.graphics.DashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
                    isAntiAlias = true
                }

                drawIntoCanvas {
                    val rect = android.graphics.RectF(0f, 0f, size.width, size.height)
                    it.nativeCanvas.drawRoundRect(rect, 25f, 25f, paint)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.UploadFile,
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = tintColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DownPaymentCard(
    modifier: Modifier = Modifier,
    cardColor: Color = appWhite,
    borderColor: Color = appWhite,
    cardHeader: String,
    image: Painter = painterResource(R.drawable.product_details),
    start: Dp = 10.dp,
    end: Dp = 10.dp,
    roundedCornerDp: Dp = 8.dp,
    top: Dp = 5.dp,
    bottom: Dp = 5.dp,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(cardColor, RoundedCornerShape(roundedCornerDp))
            .border(1.dp, borderColor, RoundedCornerShape(roundedCornerDp))
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = image,
                contentDescription = "CardImage",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = cardHeader,
                style = normal16Text700,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        HorizontalDivider(color = appOrange, start = 0.dp, end = 0.dp, top = 0.dp)
        content()
    }
}
