package com.github.sugunasriram.fisloanlibv4.fis_code.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomModalBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    sheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    sheetBackgroundColor: Color = appWhite,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = sheetShape,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContent = { sheetContent() }
    ) {
        content()
    }
}
