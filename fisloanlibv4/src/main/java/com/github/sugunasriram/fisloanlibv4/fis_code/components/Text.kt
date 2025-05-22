package com.github.sugunasriram.fisloanlibv4.fis_code.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.gray90
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal26Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal36Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.readableTextGray

@Composable
fun StartingText(
    text: String,
    textColor: Color = appOrange,
    modifier: Modifier = Modifier,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    style: TextStyle = normal14Text400,
    textAlign: TextAlign = TextAlign.Center,
    backGroundColor: Color = Color.Transparent,
    alignment: Alignment = Alignment.TopStart,
    textOverflow: TextOverflow = TextOverflow.Clip
) {
    Box(
        contentAlignment = alignment,
        modifier = modifier
            .fillMaxWidth()
            .background(backGroundColor)
            .padding(top = top, bottom = bottom, start = start, end = end)
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            textAlign = textAlign,
            maxLines = 1,
            overflow = textOverflow
        )
    }
}

@Composable
fun RegisterText(
    text: String,
    textColor: Color = appBlack,
    modifier: Modifier = Modifier,
    top: Dp = 0.dp,
    style: TextStyle = normal36Text700,
    textAlign: TextAlign = TextAlign.Center,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    boxAlign: Alignment = Alignment.Center
) {
    Box(
        contentAlignment = boxAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = top, bottom = bottom, start = start, end = end)
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            textAlign = textAlign
        )
    }
}

@Composable
fun MultiStyleText(
    text1: String,
    color1: Color,
    text2: String,
    color2: Color,
    style1: TextStyle = normal26Text700,
    style2: TextStyle = normal26Text700,
    start: Dp = 20.dp,
    top: Dp = 30.dp,
    bottom: Dp = 0.dp,
    arrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row(
        horizontalArrangement = arrangement,
        modifier = Modifier
            .padding(top = top, start = start, bottom = bottom)
            .fillMaxWidth()
    ) {
        Text(text = text1, color = color1, style = style1)
        Text(text = text2, color = color2, style = style2)
    }
}

@Composable
fun MultipleColorText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = appBlack,
    start: Dp = 8.dp,
    resendOtpColor: Color = appOrange,
    end: Dp = 8.dp,
    top: Dp = 8.dp,
    bottom: Dp = 8.dp,
    style: TextStyle = normal16Text500,
    onClick: () -> Unit
) {
    val isClickable = resendOtpColor == appOrange
    val annotatedString = AnnotatedString.Builder().apply {
        append(text)
        val resendOtpIndex = text.indexOf("Resend OTP")
        if (resendOtpIndex != -1) {
            addStyle(SpanStyle(color = resendOtpColor, textDecoration = TextDecoration.Underline), resendOtpIndex, text.length)
        }
    }.toAnnotatedString()

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = annotatedString,
            color = textColor,
            style = style,
            modifier = modifier
                .then(if (isClickable) Modifier.clickable { onClick() } else Modifier)
                .padding(start = start, end = end, top = top, bottom = bottom)
        )
    }
}

@Composable
fun OnlyReadAbleText(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = readableTextGray,
    textColorValue: Color = appBlack,
    start: Dp = 20.dp,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    style: TextStyle = normal14Text400,
    showImage: Boolean = false,
    textValueAlignment: TextAlign = TextAlign.Start,
    image: Painter = painterResource(R.drawable.person_icon)
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top, // 👈 this is key
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        if (showImage) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier
                    .size(26.dp)
                    .padding(end = 10.dp)
            )
        }

        Text(
            text = textHeader,
            style = style,
            color = textColorHeader,
            modifier = Modifier
                .weight(0.7f)
                .padding(top = 4.dp) // Optional for better visual alignment
        )

        Text(
            text = textValue,
            style = style,
            color = textColorValue,
            modifier = Modifier
                .weight(1f)
                .padding(top = 4.dp), // Optional for alignment
            textAlign = textValueAlignment
        )
    }
}

@Composable
fun EditableText(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = readableTextGray,
    textColorValue: Color = appBlack,
    start: Dp = 20.dp,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    style: TextStyle = normal14Text400,
    showImage: Boolean = false,
    image: Painter = painterResource(R.drawable.person_icon),
    onClickEdit: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        if (showImage) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier.size(24.dp).padding(end = 10.dp)
            )
        }
        Text(
            text = textHeader,
            style = style,
            color = textColorHeader,
            modifier = Modifier.weight(0.8f)
        )
        Text(
            text = textValue,
            style = style,
            color = textColorValue,
            modifier = Modifier.weight(0.9f),
            textAlign = TextAlign.Start
        )
        Image(
            painter = painterResource(R.drawable.edit_icon),
            contentDescription = "",
            modifier = Modifier.size(25.dp).padding(end = 10.dp)
                .clickable { onClickEdit() }
        )
    }
}

@Composable
fun HeaderWithValue(
    textHeader: String,
    headerColor: Color = appWhite,
    textValue: String,
    valueColor: Color = appWhite,
    modifier: Modifier = Modifier,
    start: Dp = 10.dp,
    end: Dp = 10.dp,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    headerStyle: TextStyle = normal12Text400,
    valueStyle: TextStyle = normal14Text700,
    headerTextAlign: TextAlign = TextAlign.Start,
    valueTextAlign: TextAlign = TextAlign.Start
) {
    Column(
        modifier = modifier.padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(
            text = textHeader,
            style = headerStyle,
            color = headerColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = headerTextAlign
        )
        Text(
            text = textValue,
            style = valueStyle,
            color = valueColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = valueTextAlign
        )
    }
}

@Composable
fun BulletImageWithText(
    @DrawableRes image: Int = R.drawable.arrow_icon,
    start: Dp = 15.dp,
    end: Dp = 15.dp,
    text: String = stringResource(id = R.string.share_bank_statements_instantly),
    top: Dp = 25.dp,
    bottom: Dp = 0.dp
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth().padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Text(
            text = text,
            style = normal14Text400,
            color = appBlack,
            modifier = Modifier.padding(start = 15.dp)
        )
    }
}

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    textColor: Color = appOrange,
    start: Dp = 8.dp,
    end: Dp = 8.dp,
    top: Dp = 8.dp,
    bottom: Dp = 8.dp,
    style: TextStyle = normal14Text500,
    onSahamatiClick: () -> Unit,
    onRbiClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("visit ")

        pushStringAnnotation(tag = "URL", annotation = "sahamati")
        withStyle(style = SpanStyle(color = textColor)) { append("Sahamati") }

        pop()
        append(" or ")

        pushStringAnnotation(tag = "URL", annotation = "rbi")
        withStyle(style = SpanStyle(color = textColor)) { append("RBI") }

        pop()
        append(" website to know more")
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp, end = 5.dp)
    ) {
        androidx.compose.foundation.text.ClickableText(
            text = annotatedString,
            style = style.copy(color = Color.Black),
            modifier = modifier
                .padding(start = start, end = end, top = top, bottom = bottom),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        when (annotation.item) {
                            "sahamati" -> onSahamatiClick()
                            "rbi" -> onRbiClick()
                        }
                    }
            }
        )
    }
}

@Composable
fun HeaderValueInARow(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = gray90,
    textColorValue: Color = appBlack,
    start: Dp = 10.dp,
    headerStyle: TextStyle = normal14Text400,
    valueStyle: TextStyle = normal14Text500,
    valueTextAlign: TextAlign = TextAlign.End,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    isLoanDetails: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth().background(if (isLoanDetails)appWhite else Color.Transparent)
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        val textModifier = if (isLoanDetails) {
            Modifier.weight(1f)
        } else {
            Modifier
        }
        Text(
            text = textHeader,
            style = headerStyle,
            color = textColorHeader,
            modifier = textModifier
        )
        Text(
            text = textValue,
            style = valueStyle,
            color = textColorValue,
            modifier = textModifier,
            textAlign = valueTextAlign
        )
    }
}

@Composable
fun HeaderValueWithTextBelow(
    textHeader: String,
    textColorHeader: Color = gray90,
    modifier: Modifier = Modifier,
    textHeaderStyle: TextStyle = normal14Text400,
    textValue: String,
    top: Dp = 8.dp,
    textColorValue: Color = appBlack,
    textValueStyle: TextStyle = normal14Text500,
    textBelowValue: String,
    textColorBelowValue: Color = appGray,
    bottom: Dp = 0.dp,
    textBelowStyle: TextStyle = normal12Text400,
    start: Dp = 15.dp,
    end: Dp = 15.dp
) {
    Column(
        modifier = modifier
            .fillMaxWidth().background(appWhite)
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = textHeader,
                style = textHeaderStyle,
                color = textColorHeader,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = textValue,
                style = textValueStyle,
                color = textColorValue,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = textBelowValue,
                style = textBelowStyle,
                color = textColorBelowValue,
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                textAlign = TextAlign.End,
                softWrap = true
            )
        }
        HorizontalDivider(start = 0.dp, end = 0.dp, top = 4.dp)
    }
}

@Composable
fun ClickableHeaderValueWithTextBelow(
    textHeader: String,
    textColorHeader: Color = gray90,
    textValue: String,
    textHeaderStyle: TextStyle = normal14Text400,
    textColorValue: Color = appOrange,
    textValueStyle: TextStyle = normal14Text400,
    textBelowValue: String,
    start: Dp = 15.dp,
    end: Dp = 15.dp,
    textBelowStyle: TextStyle = normal12Text400,
    modifier: Modifier = Modifier,
    textColorBelowValue: Color = appGray,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth().background(appWhite)
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = textHeader,
                style = textHeaderStyle,
                color = textColorHeader,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = textValue,
                style = textValueStyle,
                color = textColorValue,
                modifier = Modifier.weight(1f).clickable { onClick() },
                textAlign = TextAlign.End
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = textBelowValue,
                style = textBelowStyle,
                color = textColorBelowValue,
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                textAlign = TextAlign.End,
                softWrap = true
            )
        }
        HorizontalDivider(start = 0.dp, end = 0.dp, top = 4.dp)
    }
}
