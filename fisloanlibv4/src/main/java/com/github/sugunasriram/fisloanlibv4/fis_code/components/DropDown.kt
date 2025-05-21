package com.github.sugunasriram.fisloanlibv4.fis_code.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500Orange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text400

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomDropDownField(
    start: Dp = 15.dp, end: Dp = 15.dp, top: Dp = 10.dp, bottom: Dp = 0.dp, selectedText: String,
    hint: String, expand: Boolean, setExpand: (Boolean) -> Unit, itemList: List<String>,
    focus: FocusRequester = FocusRequester.Default,
    onNextFocus: FocusRequester = FocusRequester.Default, error: String? = null,
    onDismiss: () -> Unit, onItemSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val formattedText = selectedText.replaceFirstChar { it.uppercase() }
    ExposedDropdownMenuBox(
        expanded = expand,
        onExpandedChange = { setExpand(!expand) },
        modifier = Modifier
            .padding(start = start, end = end, top = top, bottom = bottom)
            .fillMaxWidth()
    ) {
        Column() {
            OutlinedTextField(
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focus)
                    .clickable {
                        setExpand(!expand)
                        if (!expand) {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    },
                placeholder = { Text(hint) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { onNextFocus.requestFocus() }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = appTheme,
                    unfocusedBorderColor = appTheme,
                    cursorColor = cursorColor,
                    errorBorderColor = errorRed
                ),
                value = formattedText,
                onValueChange = {},
                textStyle = normal18Text400,
                isError = error?.isNotEmpty() == true,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_down),
                        contentDescription = stringResource(id = R.string.down_ward_image),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                setExpand(!expand)
                                if (!expand) {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            }
                    )
                }
            )
            if (!error.isNullOrEmpty()) {
                Text(
                    text = error, style = normal12Text400,
                    color = errorRed,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }
        }

        // Dropdown Menu inside the same Box
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .background(appWhite, shape = RoundedCornerShape(16.dp))
        ) {
            itemList.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(label)
                        setExpand(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (label == selectedText) appOrange else Color.Transparent)
                ) {
                    Text(
                        text = label,
                        style = normal20Text400,
                        color = if (label == selectedText) appWhite else appBlack
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownField(
    start: Dp = 20.dp, end: Dp = 20.dp, top: Dp = 10.dp, bottom: Dp = 0.dp, selectedText: String,
    hint: String, expand: Boolean, setExpand: (Boolean) -> Unit, itemList: List<String>,
    focus: FocusRequester,modifier: Modifier,
    onNextFocus: FocusRequester = FocusRequester.Default, error: String? = null,
    onDismiss: () -> Unit, onItemSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val formattedText = selectedText.replaceFirstChar { it.uppercase() }
    ExposedDropdownMenuBox(
        expanded = expand,
        onExpandedChange = { setExpand(!expand) },
        modifier = Modifier
            .padding(start = start, end = end, top = top, bottom = bottom)
            .fillMaxWidth()
    ) {
        InputField(
            inputText = formattedText,
            textStyle =  if (selectedText == "Loan Purpose") normal16Text500Orange else normal16Text500,
            hint = hint,
            start = 0.dp,
            end = 0.dp,
            top = 0.dp,
            readOnly = true,
            modifier = modifier.focusRequester(focus)
                .clickable {
                    setExpand(!expand)
                    if (!expand) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { onNextFocus.requestFocus() }),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = backgroundOrange,
                cursorColor = cursorColor,
                errorBorderColor = errorRed
            ),
            onValueChange = {},
            error = error ?: "",
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = stringResource(id = R.string.down_ward_image),
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            setExpand(!expand)
                            if (!expand) {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        }
                )
            }
        )

        // Dropdown Menu inside the same Box
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .background(appWhite, shape = RoundedCornerShape(16.dp))
        ) {
            itemList.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(label)
                        setExpand(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (label == selectedText) appOrange else Color.Transparent)
                ) {
                    Text(
                        text = label,
                        style = normal16Text500,
                        color = if (label == selectedText) appWhite else appBlack
                    )
                }
            }
        }
    }
}


