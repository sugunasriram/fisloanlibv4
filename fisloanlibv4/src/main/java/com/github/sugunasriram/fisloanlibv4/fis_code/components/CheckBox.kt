package com.github.sugunasriram.fisloanlibv4.fis_code.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.primaryOrange

@Composable
fun CheckBoxText(
    boxState: Boolean, textColor: Color = appBlack, modifier: Modifier = Modifier,
    style: TextStyle = normal14Text400, start: Dp = 0.dp, end: Dp = 10.dp, bottom: Dp = 10.dp,
    top: Dp = 0.dp, boxStart: Dp = 10.dp, text: String, boxBackground: Color = Color.Transparent,
    contentArrangement:Arrangement.Horizontal=Arrangement.Center,checkedColor:Color= appOrange,
    uncheckedColor:Color= appOrange,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = start, bottom = bottom, end = end, top = top).
        background(boxBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = contentArrangement
    ) {
        Checkbox(
            checked = boxState, onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = boxStart),
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor,
            ),
        )
        Text(
            text = text, style = style, color = textColor, textAlign = TextAlign.Justify,
            modifier = modifier
                .clickable { onCheckedChange(!boxState) }
                .padding(end = 20.dp, top = 4.dp, bottom = 4.dp),
        )
    }
}

@Composable
fun OnlyCheckBox(
    boxState: Boolean, modifier: Modifier = Modifier, start: Dp = 30.dp, end: Dp = 10.dp,
    bottom: Dp = 10.dp, top: Dp = 0.dp, onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        modifier = modifier.padding(start = start, bottom = bottom, end = end, top = top),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = boxState, onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 10.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = primaryOrange,
                uncheckedColor = primaryOrange,
            ),
        )
    }
}

@Composable
fun ImageTextWithCheckbox(
    backGroundColor: Color = appWhite,
    radioOptions: ArrayList<BankItem?>,
    top: Dp = 15.dp,
    selectedOptions: List<String>,
    onOptionSelected: (List<String>) -> Unit
) {
    Column {
        radioOptions.forEach { data ->
            data?.let {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            val updatedList = selectedOptions.toMutableList()
                            if (updatedList.contains(it.bankName)) {
                                updatedList.remove(it.bankName)
                            } else {
                                updatedList.add(it.bankName ?: "")
                            }
                            onOptionSelected(updatedList)
                        }
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = top)
                        .background(backGroundColor)
                ) {
                    data.imageBank?.let { imageUrl ->
                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                                .apply {
                                    crossfade(true)
                                    placeholder(R.drawable.bank_icon)
                                }.build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = stringResource(id = R.string.bank_image),
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    data.bankName?.let { bankName ->
                        Text(
                            text = bankName,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
                            style = bold14Text500,
                            textAlign = TextAlign.Start,
                        )
                    }
                    Checkbox(
                        checked = selectedOptions.contains(it.bankName),
                        onCheckedChange = { isChecked ->
                            val updatedList = selectedOptions.toMutableList()
                            if (isChecked) {
                                data.bankName?.let { updatedList.add(it) }
                            } else {
                                data.bankName?.let { updatedList.remove(it) }
                            }
                            onOptionSelected(updatedList)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = appOrange
                        )
                    )
                }
            }
        }
    }
}




