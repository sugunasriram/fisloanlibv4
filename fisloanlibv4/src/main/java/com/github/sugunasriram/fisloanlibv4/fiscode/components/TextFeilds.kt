package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.InvoicesItem
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hyperTextColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.primaryOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.OtpViewModel
import java.util.Locale

@Composable
fun OutlinedTextFieldValidation(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    error: String? = null,
    modifier: Modifier = Modifier.fillMaxWidth(0.8f),
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
    trailingIcon: @Composable (() -> Unit)? = {
        if (error?.isNotEmpty() == true) {
            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
        }
    },
    isRadioButton: Boolean = false
) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth().height(58.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            OutlinedTextField(
                enabled = enabled,
                readOnly = readOnly,
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                singleLine = singleLine,
                textStyle = textStyle.copy(textAlign = TextAlign.Center),
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = error?.isNotEmpty() == true,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                maxLines = maxLines,
                interactionSource = interactionSource,
                shape = shape,
                colors = colors

            )
        }
        if (!isRadioButton) {
            if (!error.isNullOrEmpty()) {
                Text(
                    text = error,
                    style = normal12Text400,
                    color = errorRed,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun InputField(
    inputText: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    start: Dp = 40.dp,
    end: Dp = 40.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    hint: String = "",
    hintAlign: TextAlign = TextAlign.Start,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enable: Boolean = true,
    readOnly: Boolean = false,
    error: String? = null,
    textStyle: TextStyle = normal16Text500,
    showOnlyTextField: Boolean = true,
    showLeadingImage: Boolean = true,
    leadingImage: Painter = painterResource(id = R.drawable.lock),

    showTopText: Boolean = true,
    topText: String = stringResource(id = R.string.first_name),
    showStar: Boolean = false,
    showOptional: Boolean = false,
    topTextStart: Dp = 40.dp,
    topTextBottom: Dp = 0.dp,
    topTextTop: Dp = 8.dp,

    showBox: Boolean = false,
    boxText: String = "",
    boxHeight: Dp = 60.dp,
    boxClick: (() -> Unit)? = null,
    boxFocusRequester: FocusRequester = FocusRequester(),

    showRadio: Boolean = false,
    radioList: List<String> = listOf("Option 1", "Option 2", "Option 3"),
    selectedRadio: String = radioList.first(),
    onRadioSelected: (String) -> Unit = {},
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = appTheme,
        unfocusedBorderColor = backgroundOrange,
        cursorColor = cursorColor,
        errorBorderColor = errorRed,
        disabledBorderColor = Color.Transparent
    )
) {
    if (showOnlyTextField) {
        OutlinedTextFieldValidation(
            value = inputText,
            onValueChange = onValueChange,
            modifier = modifier
                .padding(start = start, end = end, top = top, bottom = bottom)
                .fillMaxWidth(),
            readOnly = readOnly,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            label = {
                Text(
                    text = hint,
                    color = hintGray,
                    style = normal18Text500,
                    textAlign = hintAlign
                )
            },
            colors = if (readOnly) {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = cursorColor,
                    errorBorderColor = errorRed,
                    disabledBorderColor = Color.Transparent
                )
            } else {
                colors
            },
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enabled = enable,
            error = error ?: ""
        )
    } else {
        Column() {
            if (showTopText) {
                OutLineTextFieldHeader(
                    topText = topText,
                    showOptional = showOptional,
                    showStar = showStar,
                    topTextStart = topTextStart,
                    topTextBottom = topTextBottom,
                    topTextTop = topTextTop
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                if (showLeadingImage) {
                    Image(
                        painter = leadingImage,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp).size(24.dp)
                    )
                }
                if (!showBox) {
                    OutlinedTextFieldValidation(
                        value = inputText,
                        onValueChange = onValueChange,
                        modifier = modifier
                            .padding(start = start, end = end, top = top, bottom = bottom)
                            .fillMaxWidth(),
                        readOnly = readOnly,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                text = hint,
                                color = hintGray,
                                style = normal14Text500,
                                textAlign = hintAlign
                            )
                        },
                        colors = if (readOnly) {
                            TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = cursorColor,
                                errorBorderColor = errorRed,
                                disabledBorderColor = Color.Transparent
                            )
                        } else {
                            colors
                        },
                        textStyle = textStyle,
                        visualTransformation = visualTransformation,
                        trailingIcon = trailingIcon,
                        leadingIcon = leadingIcon,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        enabled = enable,
                        error = error ?: ""
                    )
                } else {
                    Column() {
                        if (showRadio) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                OutlinedTextFieldValidation(
                                    isRadioButton = true,
                                    value = inputText,
                                    onValueChange = {},
                                    modifier = modifier.fillMaxWidth(),
                                    readOnly = true,
                                    keyboardOptions = KeyboardOptions.Default,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = appTheme,
                                        unfocusedBorderColor = Color.Transparent,
                                        cursorColor = Color.Transparent,
                                        errorBorderColor = errorRed,
                                        disabledBorderColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    singleLine = true,
                                    textStyle = textStyle,
                                    visualTransformation = visualTransformation,
                                    trailingIcon = trailingIcon,
                                    leadingIcon = leadingIcon,
                                    enabled = enable,
                                    error = error ?: ""

                                )
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    radioList.forEach { option ->
                                        RadioButtonWithText(
                                            text = option,
                                            readOnly = readOnly,
                                            selected = selectedRadio == option,
                                            onCheckedChange = { isSelected ->
                                                if (isSelected) {
                                                    onRadioSelected(option)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            if (!error.isNullOrEmpty()) {
                                Text(
                                    text = error,
                                    style = normal12Text400,
                                    color = errorRed,
                                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .focusRequester(boxFocusRequester)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .fillMaxWidth()
                                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                                    .border(
                                        width = 1.dp,
                                        color = if (error.isNullOrEmpty()) Color.Transparent else errorRed, // Red border on error
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clip(RoundedCornerShape(8.dp))

                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(vertical = 10.dp, horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = boxText,
                                        style = normal16Text500,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(5.dp).weight(0.9f),
                                        minLines = 3,
                                        maxLines = 6
                                    )
                                    if (!readOnly) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.edit_icon),
                                            contentDescription = "",
                                            modifier = Modifier.size(20.dp).weight(0.1f)
                                                .clickable {
                                                    if (boxClick != null) {
                                                        boxClick()
                                                    }
                                                }
                                        )
                                    }
                                }
                            }
//                            if (!error.isNullOrEmpty()) {
//                                Text(
//                                    text = error, style = normal12Text400,
//                                    color = errorRed,
//                                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
//                                )
//                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OutLineTextFieldHeader(
    topText: String,
    showOptional: Boolean,
    showStar: Boolean,
    topTextStart: Dp,
    topTextBottom: Dp,
    topTextTop: Dp,
    starTop: Dp = 8.dp
) {
    Row() {
        Text(
            text = topText,
            color = if (showOptional) appBlack else appOrange,
            style = normal14Text400,
            modifier = Modifier.padding(
                start = topTextStart,
                bottom = topTextBottom,
                top = topTextTop
            )
        )
        if (showOptional) {
            Text(
                text = "(Optional)",
                color = hintGray,
                style = normal14Text400,
                modifier = Modifier.padding(bottom = 0.dp, top = 8.dp)
            )
        }
        if (showStar) {
            Text(
                text = " *",
                color = appRed,
                style = normal14Text400,
                modifier = Modifier.padding(bottom = 0.dp, top = starTop)
            )
        }
    }
}

@Composable
fun SignUpText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = hyperTextColor,
    start: Dp = 8.dp,
    end: Dp = 8.dp,
    top: Dp = 8.dp,
    bottom: Dp = 8.dp,
    style: TextStyle = normal14Text500,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            modifier = modifier
                .clickable { onClick() }
                .padding(start = start, end = end, top = top, bottom = bottom)
        )
    }
}

@Composable
fun HyperText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = primaryOrange,
    start: Dp = 8.dp,
    end: Dp = 8.dp,
    top: Dp = 8.dp,
    bottom: Dp = 8.dp,
    boxTop: Dp = 0.dp,
    alignment: Alignment = Alignment.TopEnd,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = alignment,
        modifier = modifier
            .padding(top = boxTop)
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.wrapContentWidth()
                .background(backgroundOrange, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = text,
                color = textColor,
                modifier = modifier
                    .clickable { onClick() }
                    .padding(start = start, top = top, bottom = bottom, end = end),
                style = normal20Text500
            )
        }
    }
}

@Composable
fun LanguageText(
    language: String,
    showIcon: Boolean,
    style: TextStyle = normal14Text400,
    start: Dp = 24.dp,
    end: Dp = 24.dp,
    top: Dp = 10.dp,
    bottom: Dp = 10.dp,
    height: Dp = 35.dp,
    width: Dp = 1.dp,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(text = language, style = style)
        if (showIcon) {
            Image(
                painter = painterResource(id = R.drawable.correct_icon),
                contentDescription = stringResource(id = R.string.correct_icon)
            )
        }
    }
}

@Composable
fun SpaceBetweenText(
    offer: Boolean = false,
    text: String = "",
    value: String = "",
    top: Dp = 20.dp,
    image: Painter = painterResource(id = R.drawable.app_logo),
    showImage: Boolean = false,
    showText: Boolean = true,
    start: Dp = 10.dp,
    end: Dp = 10.dp,
    color: Color = appOrange,
    offerText: String = stringResource(id = R.string.order_value),
    style: TextStyle = normal14Text400,
    textColor: Color = appBlack
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = 10.dp)
    ) {
        if (showImage) {
            Image(
                painter = image,
                contentDescription = stringResource(id = R.string.bank_image),
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            if (showText) {
                Image(
                    painter = painterResource(id = R.drawable.forward_icon),
                    contentDescription = stringResource(id = R.string.bank_image)
                )
            } else {
                Text(text = text, style = style, color = color)
            }
        } else {
            Text(text = text, style = style, color = textColor)
            Column(horizontalAlignment = Alignment.End) {
                Text(text = value, style = style, color = textColor)
                if (offer) {
                    Text(text = offerText, style = style, modifier = Modifier.padding(0.dp))
                }
            }
        }
    }
}

@Composable
fun TextValueInARow(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = appBlueTitle,
    textColorValue: Color = appBlueTitle,
    start: Dp = 10.dp,
    style: TextStyle = normal14Text400,
    style1: TextStyle = normal20Text700,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(
            text = textHeader,
            style = style,
            color = textColorHeader,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = textValue,
            style = style1,
            color = textColorValue,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun TextHyphenValueInARow(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = appBlueTitle,
    textColorValue: Color = appBlueTitle,
    start: Dp = 10.dp,
    style: TextStyle = normal14Text400,
    style1: TextStyle = normal20Text700,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(
            text = textHeader,
            style = style,
            color = textColorHeader,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = " - ",
            style = style1,
            color = textColorValue,
            modifier = Modifier.weight(0.15f),
            textAlign = TextAlign.Start
        )
        Text(
            text = textValue,
            style = style1,
            color = textColorValue,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SpaceBetweenTextIcon(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes image: Int,
    style: TextStyle = normal16Text700,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 10.dp,
    bottom: Dp = 10.dp,
    height: Dp = 35.dp,
    width: Dp = 1.dp,
    textColor: Color = appBlack,
    backGroundColor: Color = Color.Transparent,
    imageSize: Dp = 30.dp,
    textStart: Dp = 0.dp,
    imageEnd: Dp = 8.dp,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = start, end = end, top = top, bottom = bottom)
            .background(color = backGroundColor)
    ) {
        Text(
            text = text,
            style = style,
            color = textColor,
            modifier = modifier.padding(start = textStart)
        )
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.correct_icon),
            modifier
                .clickable { onClick() }
                .padding(start = 4.dp, top = 4.dp, bottom = 8.dp, end = imageEnd)
                .size(imageSize)
        )
    }
}

@Composable
fun HeaderNextRowValue(
    textHeader: String,
    textValue: String,
    modifier: Modifier,
    textColorHeader: Color = appBlack,
    textColorValue: Color = appBlueTitle,
    rowBackground: Color = Color.Transparent,
    start: Dp = 10.dp,
    end: Dp = 20.dp,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    style: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
        fontWeight = FontWeight(400)
    )
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(1f)
            .background(color = rowBackground)
    ) {
        Text(
            text = textHeader,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontWeight = FontWeight(400)
            ),
            color = textColorHeader.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = textValue,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontWeight = FontWeight(400)
            ),
            color = textColorValue,
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun TextInputLayout(
    text: String = "",
    textFieldVal: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    hintText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.robotocondensed_semibold)),
        fontWeight = FontWeight(400),
        color = appOrange
    )
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        var newText: String = text.replace(hintText.toLowerCase(Locale.ROOT), "").trim()
        if (isFocused) {
            Text(
                text = hintText,
                color = hintGray,
                style = normal18Text400,
                textAlign = TextAlign.Start
            )
            newText = textFieldVal.text.replace(hintText.toLowerCase(Locale.ROOT), "").trim()
            if (newText.isEmpty() || newText.equals("0")) {
                newText = ""
            }
        }
        OutlinedTextField(
            value = textFieldVal,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth().background(appWhite, RoundedCornerShape(16.dp))
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            keyboardOptions = keyboardOptions,
            textStyle = style.copy(textAlign = TextAlign.Center),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = appTheme,
                cursorColor = cursorColor
            ),
            readOnly = readOnly,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun TextInputLayoutForTenure(
    text: String = "",
    onTextChanged: (String) -> Unit,
    hintText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        var newText: String

        // Display hint text only when the field is not focused and empty
        if (isFocused) {
            Text(
                text = hintText,
                color = hintGray,
                style = normal18Text400,
                textAlign = TextAlign.Start
            )
            newText = text.replace(hintText.lowercase(Locale.ROOT), "").trim()
            if (newText.isEmpty() || newText.equals("0")) {
                newText = ""
            }
            TextFieldValue(newText)
        } else {
            Text(text = "")
            newText = text
        }
        OutlinedTextField(
            value = newText,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },

            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = appOrange,
                fontFamily = FontFamily(Font(R.font.robotocondensed_bold)),
                fontWeight = FontWeight(800)

            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = appTheme,
                cursorColor = cursorColor
            ),
            readOnly = readOnly
        )
    }
}

@Composable
fun InputView(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    onKeyEvent: (KeyEvent) -> Boolean
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = false,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .focusRequester(focusRequester)
            .onKeyEvent(onKeyEvent)
            .width(40.dp)
            .height(50.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx() // Adjust thickness of the underline
                drawLine(
                    color = appOrange, // Change this to your desired line color
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            },
//
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(appOrange),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun OtpView(
    textList: List<MutableState<TextFieldValue>>,
    requestList: List<FocusRequester>,
    pastedEvent: () -> Unit
) {
    val otpViewModel: OtpViewModel = viewModel()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.dp) // Sugu1
                .padding(top = 30.dp)
                .align(Alignment.TopCenter)
        ) {
            for (i in textList.indices) {
                InputView(
                    value = textList[i].value,
                    onValueChange = { newValue ->
                        // pasted event
                        if (newValue.text.length - textList[i].value.text.length > 1) {
                            pastedEvent()
                            return@InputView
                        }
                        // Filter to only allow single digits
                        val filteredValue = newValue.text.filter { it.isDigit() }.take(1)

                        otpViewModel.updateOtpError(null)
                        // Check if the filtered value is empty and handle backspace
                        if (filteredValue.isEmpty() && textList[i].value.text.isNotEmpty()) {
                            textList[i].value = TextFieldValue("")
                            if (i > 0) {
                                requestList[i - 1].requestFocus()
                            }
                            return@InputView
                        }

                        // Set the value and move cursor to the end
                        textList[i].value = TextFieldValue(
                            text = filteredValue,
                            selection = TextRange(filteredValue.length)
                        )

                        // Move focus to the next field if the current one is filled
//                        if (filteredValue.length == 1 && i < textList.size - 1) {
//                            requestList[i + 1].requestFocus()
//                        }
                        if (filteredValue.length == 1) {
                            if (i < textList.size - 1) {
                                requestList[i + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    },
                    focusRequester = requestList[i],
                    onKeyEvent = { keyEvent ->
                        if (keyEvent.key == Key.Backspace && keyEvent.type == KeyEventType.KeyUp &&
                            textList[i].value.text.isEmpty() && i > 0
                        ) {
                            requestList[i - 1].requestFocus()
                            true
                        } else {
                            false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GstTransactionCard(
    boxState: Boolean = true,
    showCheckBox: Boolean = true,
    start: Dp = 8.dp,
    end: Dp = 0.dp,
    top: Dp = 8.dp,
    bottom: Dp = 8.dp,
    onCheckedChange: ((Boolean) -> Unit),
    onClick: () -> Unit,
    invoiceData: InvoicesItem?
) {
    Row(
        modifier = Modifier
            .padding(start = start, top = top, bottom = bottom, end = end)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() }
                .fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    invoiceData?.ctin?.let { ctin ->
                        Text(
//                        text = "Padmavati Steel Corporation Pvt. Ltd.",
                            text = "$ctin",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
//                    invoiceData?.ctin?.let { ctin ->
//                        invoiceData.idt?.let { date ->
//                            val dateString = date
//                            val actualDate = dateString.split("T")[0]
//                            Text(
//                                text = "$actualDate • $ctin",
//                                color = Color.Gray,
//                                fontSize = 12.sp
//                            )
//                        }
//                    }
                    invoiceData?.inum?.let { inum ->
                        invoiceData.idt?.let { date ->
                            val actualDate = CommonMethods().editingDate(date = date)
                            Text(
                                text = "$actualDate • $inum",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                invoiceData?.value?.let { inVoiceAmount ->
                    Text(
                        text = "₹ $inVoiceAmount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(id = R.string.forward_logo),
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if (showCheckBox) {
            OnlyCheckBox(boxState = boxState, onCheckedChange = onCheckedChange, start = 0.dp)
        }
    }
}

@Composable
fun OnlyClickAbleText(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = appBlueTitle,
    textColorValue: Color = appOrange,
    start: Dp = 10.dp,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp,
    style: TextStyle = normal14Text400,
    textValueAlignment: TextAlign = TextAlign.Start,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(
            text = textHeader,
            style = style,
            color = textColorHeader,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = textValue,
            style = style,
            color = textColorValue,
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() },
            textAlign = textValueAlignment
        )
    }
}

@Composable
fun ClickableHeaderValueInARow(
    textHeader: String,
    textValue: String,
    modifier: Modifier = Modifier,
    end: Dp = 20.dp,
    textColorHeader: Color = appBlueTitle,
    textColorValue: Color = appBlueTitle,
    start: Dp = 10.dp,
    headerStyle: TextStyle = normal14Text400,
    valueStyle: TextStyle = normal20Text700,
    valueTextAlign: TextAlign = TextAlign.End,
    onClick: () -> Unit,
    top: Dp = 8.dp,
    bottom: Dp = 0.dp
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom)
    ) {
        Text(
            text = textHeader,
            style = headerStyle,
            color = textColorHeader,
            modifier = Modifier.weight(1f)
        )

        /* Align below code to right */
        Text(
            text = textValue,
            style = valueStyle,
            color = textColorValue,
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() },
            textAlign = valueTextAlign
        )
    }
}

@Composable
fun ContinueText(
    startText: String,
    endText: String,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start, end = end, top = top, bottom = bottom),
        horizontalArrangement = Arrangement.Start
    ) {
        StartingText(text = startText, modifier = Modifier.weight(1f), start = 5.dp)
        StartingText(
            text = endText,
            modifier = Modifier.weight(1f),
            start = 5.dp,
            style = bold14Text500
        )
    }
}

@Preview
@Composable
private fun PreviewTopBar() {
    TopBar(rememberNavController(), true)
}
