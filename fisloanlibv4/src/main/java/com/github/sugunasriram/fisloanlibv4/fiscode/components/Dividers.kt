package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD9

@Composable
fun VerticalDivider(
    color: Color = grayD6, start: Dp = 24.dp, end: Dp = 0.dp, top: Dp = 0.dp, bottom: Dp = 0.dp,
    height: Dp = 35.dp, width: Dp = 1.dp
) {
    Divider(
        color = color,
        modifier = Modifier
            .padding(start = start, end = end, top = top, bottom = bottom)
            .height(height)
            .width(width)
    )
}

@Composable
fun HorizontalDivider(
    color: Color = grayD9, start: Dp = 10.dp, end: Dp = 10.dp, top: Dp = 8.dp,bottom: Dp=0.dp,
    modifier: Modifier = Modifier.padding(start = start, end = end, top = top, bottom = bottom)
) {
    Divider(
        color = color,
        modifier = modifier.padding(start = start, end = end, top = top, bottom = bottom)
    )
}
