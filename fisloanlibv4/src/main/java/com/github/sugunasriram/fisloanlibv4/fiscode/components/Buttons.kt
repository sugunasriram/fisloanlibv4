package com.github.sugunasriram.fisloanlibv4.fiscode.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.azureBlue
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.azureBlueColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayA6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayBackground
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.primaryOrange

@SuppressLint("SuspiciousIndentation")
@Composable
fun CurvedPrimaryButtonFull(
    text: String, modifier: Modifier = Modifier, top: Dp = 10.dp, bottom: Dp = 10.dp,
    start: Dp = 40.dp, end: Dp = 40.dp, style: TextStyle = normal20Text500,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
    textColor: Color = appWhite, backgroundColor: Color = appOrange,
    enabled: Boolean = true, onClick: () -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    Text(
        text = text, style = style, color = textColor, textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = shape)
            .then(if (enabled) Modifier.clickable {
                onClick()
                controller?.hide()
            } else Modifier)
            .padding(top = top, bottom = bottom, start = start, end = end)
    )

}

@Composable
fun BackButton(navController: NavHostController, onClick: () -> Unit= { navController.popBackStack() }){
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp))
            .border(1.dp, appWhite, shape = RoundedCornerShape(8.dp))
            .background(appOrange, shape = RoundedCornerShape(8.dp))

    ){
        Image(
            painter = painterResource(id = R.drawable.back_white_icon),
            contentDescription = stringResource(id = R.string.back_icon),
            modifier = Modifier
                .clickable { onClick() }
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun VoiceRecorderButton(navController: NavHostController, onClick: () -> Unit= { }){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.voice_recorder),
            contentDescription = stringResource(id = R.string.voice_recorder_icon),
            modifier = Modifier.size(90.dp)
                .clickable {
                    onClick()
                }
                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun NextArrowButton(navController: NavHostController, onClick: () -> Unit= { }){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.round_with_arrow),
            contentDescription = stringResource(id = R.string.next),
            modifier = Modifier.size(90.dp)
                .clickable {
                    onClick()
                }
                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun CurvedPrimaryButtonMultipleInRow(
    text: String, modifier: Modifier = Modifier, top: Dp = 10.dp, bottom: Dp = 10.dp,
    start: Dp = 40.dp, end: Dp = 40.dp, style: TextStyle = normal20Text500,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
    textColor: Color = appWhite, backgroundColor: Color = appOrange, onClick: () -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = backgroundColor, shape = shape)
            .clickable {
                onClick()
                controller?.hide()
            }
            .padding(top = top, bottom = bottom, start = start, end = end),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, style = style, color = textColor, textAlign = TextAlign.Center,
        )
    }
}
@Composable
fun ImageTextButtonRow(
    imagePainter: Painter, textHeader: String, textStyle: TextStyle, textColor: Color,
    buttonText: String, buttonTextColor: Color = appOrange, buttonColor: Color = appWhite,
    buttonTextStyle: TextStyle = normal12Text400, onButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(1.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.size(16.dp)) // Space between image and text`
        Text(
            text = textHeader,
            style = textStyle,
            color = textColor,
            modifier = Modifier.weight(3f)
        )
        Spacer(modifier = Modifier.weight(1f))

        WrapBorderButton(
            text = buttonText,
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .weight(2.5f),
            style = buttonTextStyle,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = buttonColor,
            textColor = buttonTextColor,
        ) { onButtonClick() }
    }
}
@Composable
fun WrapBorderButton(
    text: String, modifier: Modifier = Modifier, top: Dp = 10.dp, bottom: Dp = 10.dp,
    start: Dp = 17.dp, end: Dp = 17.dp, boxTop: Dp = 0.dp, style: TextStyle = normal20Text500,
    shape: RoundedCornerShape = RoundedCornerShape(15.dp), textColor: Color = primaryOrange,
    backgroundColor: Color = appWhite, alignment: Alignment = Alignment.TopEnd,
    onClick: () -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    Box(
        contentAlignment = alignment,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = boxTop)
    ) {
        Text(
            text = text, style = style, color = textColor, textAlign = TextAlign.Center,
            modifier = modifier
                .clickable {
                    onClick()
                    controller?.hide()
                }
                .background(color = backgroundColor, shape = shape)
                .padding(top = top, bottom = bottom, start = start, end = end)
        )
    }
}

@Composable
fun ClickableTextWithIcon(
    text: String, @DrawableRes image: Int, textDecoration: TextDecoration? = null,
    backgroundColor: Color = grayBackground, borderColor: Color = grayD6,
    style: TextStyle = TextStyle(
        fontStyle = FontStyle.Normal, fontWeight = FontWeight.Normal, fontSize = 19.sp,
        lineHeight = 22.sp), color: Color=grayA6,
    modifier: Modifier = Modifier,
    imageStart: Dp = 15.dp, imageTop: Dp = 15.dp, imageEnd: Dp = 15.dp, imageBottom: Dp = 15.dp,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .padding(top = 30.dp)
            .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onClick() },
        ) {
            Image(
                colorFilter = ColorFilter.tint(color),
                painter = painterResource(id = image), contentDescription = "view all icon",
                modifier = modifier
                    .padding(start = imageStart,end=8.dp, top = imageTop, bottom = imageBottom)
            )
            Text(
                text = text, textDecoration = textDecoration, style = style, color = color,
                textAlign = TextAlign.Justify,
                modifier = modifier.padding(end = imageEnd),
            )
    }}
}

@Composable
fun ClickableText(
    text: String, textAlign: TextAlign = TextAlign.Justify, textDecoration: TextDecoration? = null,
    backgroundColor: Color = grayBackground, borderColor: Color = appOrange, top:Dp=30.dp,
    style: TextStyle = TextStyle(
        fontStyle = FontStyle.Normal, fontWeight = FontWeight.Normal, fontSize = 12.sp,
        lineHeight = 22.sp,
    ), textColor: Color= appOrange, roundedCornerShape:Dp=8.dp,
    horizontalPadding :Dp= 20.dp, verticalPadding:Dp = 10.dp,
    isUploadEnabled:Boolean=true,
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .alpha(if (isUploadEnabled) 1f else 0.5f)
            .padding(top = top)
            .border(1.dp, borderColor, shape = RoundedCornerShape(roundedCornerShape))
            .background(backgroundColor, shape = RoundedCornerShape(roundedCornerShape))
            .clickable(enabled = isUploadEnabled) { onClick() }
    ) {
            Text(
                text = text, textDecoration = textDecoration, style = style, color = textColor,
                textAlign = textAlign,
                modifier = modifier
                    .padding(horizontal = horizontalPadding, vertical = verticalPadding),
            )
    }
}

@Composable
fun CurvedPrimaryButton(
    text: String, modifier: Modifier = Modifier, top: Dp = 10.dp, bottom: Dp = 10.dp,
    start: Dp = 60.dp, end: Dp = 60.dp, style: TextStyle = normal20Text500,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
    textColor: Color = appWhite, backgroundColor: Color = appOrange,
    borderColor: Color= appOrange,
    enabled: Boolean=true,onClick: () -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    Text(
        text = text, style = style, color = textColor, textAlign = TextAlign.Center,
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .border(1.dp,borderColor,shape)
            .alpha(if (enabled) 1f else 0.5f)
            .then(if (enabled) Modifier.clickable {
                onClick()
                controller?.hide()
            } else Modifier)
            .padding(top = top, bottom = bottom, start = start, end = end)
    )

}

@Composable
fun BoxButton(buttonText:String=stringResource(id = R.string.accept), backgroundColor: Color= appOrange,
              textColor: Color= appWhite, modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(
        modifier = modifier.fillMaxHeight().fillMaxSize()
            .background(backgroundColor)
            .clickable {  onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText, style = normal18Text700,
            color = textColor
        )
    }
}
@Preview
@Composable
fun ClickableTextPreview() {
    ClickableText(
        text = stringResource(id = R.string.raise_issue), textAlign = TextAlign.Justify,
        backgroundColor = Color.White, borderColor = azureBlueColor, textColor = azureBlueColor
    ) { }
}

/**
 * Preview for [CurvedPrimaryButtonFull]
 */
@Preview
@Composable
fun CurvedPrimaryButtonFullPreview() {
    CurvedPrimaryButtonFull(
        text = "Accept",
        modifier = Modifier.padding(
            start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp
        ),
        backgroundColor = azureBlue, textColor = Color.White
    ) { }
}

@Preview
@Composable
fun WrapBorderButtonPreview() {
    WrapBorderButton(
        text = stringResource(id = R.string.check_now),
        modifier = Modifier.padding(
            start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp
        ),
        backgroundColor = Color.Green.copy(alpha = 0.7f), textColor = Color.White
    ) { }
}

//@Preview
//@Composable
//fun CurvedPrimaryButtonMultipleInRowPreview() {
//    CurvedPrimaryButtonMultipleInRow(
//        text = stringResource(id = R.string.view_loan_agreement).uppercase(),
//        style = normal16Text400,
//        start = 0.dp,
//        end = 0.dp,
//        top = 10.dp,
//        bottom = 10.dp,
//        modifier = Modifier
//            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
//            .height(85.dp)
//
//    ) { }
//}
