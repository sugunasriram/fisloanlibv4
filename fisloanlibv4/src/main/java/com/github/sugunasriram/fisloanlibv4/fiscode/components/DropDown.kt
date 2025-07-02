package com.github.sugunasriram.fisloanlibv4.fiscode.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500Orange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text400

@Composable
fun CustomDropDownField(
    start: Dp = 15.dp, end: Dp = 15.dp, top: Dp = 10.dp, bottom: Dp = 0.dp,
    selectedText: String,
    hint: String,
    expand: Boolean,
    setExpand: (Boolean) -> Unit,
    itemList: List<String>,
    modifier: Modifier,
    focus: FocusRequester = FocusRequester.Default,
    onNextFocus: FocusRequester = FocusRequester.Default,
    error: String? = null,
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val displayText = if (selectedText.isEmpty()) hint else selectedText.replaceFirstChar { it.uppercase() }

    Column(modifier=modifier) {
        Box(
            modifier = Modifier
                .padding(start = start, end = end, top = top, bottom = bottom)
                .fillMaxWidth().height(55.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .then(
                    if (!error.isNullOrEmpty()) Modifier.border(
                        width = 1.dp,
                        color = errorRed,
                        shape = RoundedCornerShape(8.dp)
                    ) else  Modifier.border(
                        width = 1.dp,
                        color = appOrange,
                        shape = RoundedCornerShape(8.dp)
                    )
                )
                .clickable {
                    setExpand(!expand)
                    if (!expand) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = displayText,
                    style = normal18Text400,
                    color = if (selectedText.isEmpty()) hintGray else appBlack,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = stringResource(id = R.string.down_ward_image),
                    modifier = Modifier.padding(end=5.dp)
                        .size(24.dp)
                        .clickable {
                            setExpand(!expand)
                            if (!expand) {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        }
                )
            }
            DropdownMenu(
                expanded = expand,
                onDismissRequest = onDismiss,
                modifier = Modifier
                    .fillMaxWidth().padding(horizontal = 10.dp)
                    .background(appWhite, shape = RoundedCornerShape(8.dp))
            ) {
                itemList.forEach { label ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = {
                            Text(
                                text = label,
                                style = normal20Text400,
                                color = if (label == selectedText) appWhite else appBlack
                            )
                        },
                        onClick = {
                            onItemSelected(label)
                            setExpand(false)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (label == selectedText) appOrange else Color.Transparent)
                    )
                }
            }
        }
        if (!error.isNullOrEmpty()) {
            Text(
                text = error, style = normal12Text400,
                color = errorRed,
                modifier = Modifier.padding(start = start, top = 2.dp)
            )
        }
    }
}

@Composable
fun ClickableDropDownField(
    start: Dp = 8.dp,
    end: Dp = 8.dp,
    top: Dp = 5.dp,
    bottom: Dp = 0.dp,
    errorTextStart:Dp=8.dp,
    selectedText: String,
    expand: Boolean,
    setExpand: (Boolean) -> Unit,
    itemList: List<String>,
    focus: FocusRequester,
    modifier: Modifier,
    onNextFocus: FocusRequester = FocusRequester.Default,
    error: String? = null,
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier=modifier) {
        Box(
            modifier = Modifier.focusRequester(focus)
                .padding(start = start, end = end, top = top, bottom = bottom)
                .fillMaxWidth().height(50.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .then(
                    if (!error.isNullOrEmpty()) Modifier.border(
                        width = 1.dp,
                        color = errorRed,
                        shape = RoundedCornerShape(16.dp)
                    ) else Modifier
                )
                .clickable {
                    setExpand(!expand)
                    if (!expand) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize().padding(vertical = 8.dp)
            ) {
                Text(
                    text = selectedText,
                    style = if (selectedText == "Loan Purpose") normal16Text500Orange else normal16Text500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = stringResource(id = R.string.down_ward_image),
                    modifier = Modifier.padding(end=5.dp)
                        .size(24.dp)
                        .clickable {
                            setExpand(!expand)
                            if (!expand) {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        }
                )
            }
            DropdownMenu(
                expanded = expand,
                onDismissRequest = onDismiss,
                modifier = Modifier.padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .background(appWhite, shape = RoundedCornerShape(16.dp))
            ) {
                itemList.forEach { label ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = {
                            Text(
                                text = label,
                                style = normal16Text500,
                                color = if (label == selectedText) appWhite else appBlack
                            )
                        },
                        onClick = {
                            onItemSelected(label)
                            setExpand(false)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (label == selectedText) appOrange else Color.Transparent)
                    )
                }
            }
        }
        if (!error.isNullOrEmpty()) {
            Text(
                text = error, style = normal12Text400,
                color = errorRed,
                modifier = Modifier.padding(start = errorTextStart, top = 2.dp)
            )
        }
    }

}


