package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R

@Composable
fun OtpSuccessImage(
    modifier: Modifier = Modifier,
    imageSize: Dp = 60.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = top, bottom = bottom, start = start, end = end)
    ) {
        Image(
            painter = painterResource(id = R.drawable.otp_verified_image),
            contentDescription = stringResource(id = R.string.otp_sucess_icon),
            modifier.size(imageSize)
        )
    }
}

@Composable
fun CenteredMoneyImage(
    imageSize: Dp = 0.dp,
    @DrawableRes image: Int = R.drawable.sign_in_screen_image,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.home_page_image),
            modifier = Modifier
                .size(imageSize)
                .fillMaxSize(),
            contentScale = contentScale
        )
    }
}

@Composable
fun CenteredManAtGstImage(
    imageSize: Dp = 0.dp,
    @DrawableRes image: Int = R.drawable.man_at_gst,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.home_page_image),
            modifier = Modifier
                .size(imageSize)
                .fillMaxSize(),
            contentScale = contentScale
        )
    }
}

@Composable
fun CustomImage(
    imageWidth: Dp = 0.dp,
    @DrawableRes image: Int = R.drawable.close_icon,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    imageShape: Dp = 0.dp,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    boxAlignment: Alignment = Alignment.Center,
    imageHeight: Dp = 0.dp,
    clickImage: Boolean = false,
    onImageClick: (() -> Unit)? = null
) {
    Box(
        contentAlignment = boxAlignment,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Image(
            contentDescription = null,
            contentScale = contentScale,
            painter = painterResource(image),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(imageShape))
                .size(width = imageWidth, height = imageHeight)
                .then(
                    if (clickImage && onImageClick != null) {
                        Modifier.clickable { onImageClick() }
                    } else {
                        Modifier
                    }
                )
        )
    }
}
