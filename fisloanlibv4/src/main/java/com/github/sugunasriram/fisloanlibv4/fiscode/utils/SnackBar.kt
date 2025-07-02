package com.github.sugunasriram.fisloanlibv4.fiscode.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomSnackBar(
    @DrawableRes drawableRes: Int = R.drawable.error_icon,
    message: String,
    containerColor: Color = Color.Black, bottom: Dp = 0.dp,top:Dp = 0.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottom, top = top),
        contentAlignment = Alignment.BottomCenter
    ) {
        Snackbar(
            backgroundColor = containerColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = drawableRes),
                    contentDescription = stringResource(id = R.string.error_cion),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(message)
            }
        }
    }
}

fun showSnackError(
    coroutineScope: CoroutineScope,
    snackState: SnackbarHostState,
    inputError: String?
) {
    coroutineScope.launch {
        inputError?.let { msg ->
            snackState.showSnackbar(msg, duration = SnackbarDuration.Short)
        }
    }
}
