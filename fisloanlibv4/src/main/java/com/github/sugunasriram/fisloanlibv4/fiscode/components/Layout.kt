package com.github.sugunasriram.fisloanlibv4.fiscode.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.disableColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.StatusChip
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.onRaiseIssueClick
import kotlinx.coroutines.CoroutineScope

@Composable
fun FixedTopBottomScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    showTopBar: Boolean = true,
    showBackButton: Boolean = true,
    topBarText: String = "",
    topBarBackgroundColor: Color = appOrange,
    onBackClick: (() -> Unit)? = null,
    isSelfScrollable: Boolean = false,
    showBottom: Boolean = false,
    showHyperText: Boolean = false,
    showCheckBox: Boolean = false,
    showSingleButton: Boolean = false,
    showArrowButton: Boolean = false,
    showGetMoreOffersButton: Boolean = false,
    showDoubleButton: Boolean = false,
    showTripleButton: Boolean = false,
    checkboxState: Boolean = false,
    checkBoxText: String = stringResource(id = R.string.accept),
    onCheckBoxChange: ((Boolean) -> Unit)? = null,
    showErrorMsg: Boolean = false,
    errorMsg: String? = "",
    isPrimaryButtonEnabled: Boolean = true,
    primaryButtonText: String = stringResource(id = R.string.accept),
    onArrowButtonClick: (() -> Unit)? = null,
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String = stringResource(id = R.string.decline),
    onSecondaryButtonClick: (() -> Unit)? = null,
    tertiaryButtonText: String = stringResource(id = R.string.home),
    onTertiaryButtonClick: (() -> Unit)? = null,
    backgroundColor: Color = backgroundOrange,
    buttonStart: Dp = 30.dp,
    buttonEnd: Dp = 30.dp,
    buttonTop: Dp = 10.dp,
    buttonBottom: Dp = 20.dp,
    contentStart: Dp = 5.dp,
    contentEnd: Dp = 5.dp,
    contentTop: Dp = 0.dp,
    contentBottom: Dp = 0.dp,
    scrollState: ScrollState = rememberScrollState(),
    pageContent: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        if (showTopBar) {
            TopBar(
                navController = navController,
                showBackButton = showBackButton,
                onBackClick = { onBackClick?.let { onBackClick() } },
                topBarBackgroundColor = topBarBackgroundColor,
                topBarText = topBarText
            )
        }

        if (isSelfScrollable) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = contentStart,
                        end = contentEnd,
                        top = contentTop,
                        bottom = contentBottom
                    )
                    .fillMaxSize()
            ) {
                pageContent()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = contentStart,
                        end = contentEnd,
                        top = contentTop,
                        bottom = contentBottom
                    )
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                pageContent()
            }
        }
        if (showBottom) {
            if (showHyperText) {
                HyperlinkText(
                    onSahamatiClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sahamati.org.in/"))
                        context.startActivity(intent)
                    },
                    onRbiClick = {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://rbi.org.in/"))
                        context.startActivity(intent)
                    }
                )
            }
            if (showCheckBox) {
                CheckBoxText(
                    textColor = if (checkBoxText == stringResource(R.string.select_all)) appGreen else checkBoxGray,
                    style = if (checkBoxText == stringResource(R.string.select_all)) normal16Text500 else normal12Text400,
                    boxState = checkboxState,
                    text = checkBoxText, bottom = 0.dp, start = 0.dp, end = 0.dp,
                    checkedColor = if (checkBoxText == stringResource(R.string.select_all)) appGreen else appOrange,
                    uncheckedColor = if (checkBoxText == stringResource(R.string.select_all)) appGreen else appOrange,
                    onCheckedChange = { isChecked ->
                        onCheckBoxChange?.invoke(isChecked)
                    }
                )
            }
            if (showErrorMsg) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (errorMsg != null) {
                        Text(
                            text = errorMsg,
                            style = normal12Text400,
                            color = errorRed
                        )
                    }
                }
            }
            if (showSingleButton) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurvedPrimaryButton(
                        text = primaryButtonText,
                        backgroundColor = if (isPrimaryButtonEnabled) appOrange else disableColor,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = buttonTop, bottom = buttonBottom)
                            .wrapContentWidth()
                    ) { onPrimaryButtonClick?.let { onPrimaryButtonClick() } }
                }
            }
            if (showArrowButton) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NextArrowButton(
                        navController = navController,
                        { onArrowButtonClick?.let { onArrowButtonClick() } }
                    )
                }
            }

            if (showGetMoreOffersButton) {
                BoxButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = primaryButtonText,
                    showAddOnButtonText = true,
                ) {
                    onPrimaryButtonClick?.let { onPrimaryButtonClick() }
                }
            }

            if (showDoubleButton) {
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .border(1.dp, appOrange),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BoxButton(
                        modifier = Modifier.weight(1f),
                        buttonText = secondaryButtonText,
                        textColor = appOrange,
                        backgroundColor = appWhite
                    ) {
                        onSecondaryButtonClick?.let { onSecondaryButtonClick() }
                    }
                    BoxButton(modifier = Modifier.weight(1f), buttonText = primaryButtonText) {
                        onPrimaryButtonClick?.let { onPrimaryButtonClick() }
                    }
                }
            }
            if (showTripleButton) {
                Row(
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                        .background(backgroundOrange),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    CurvedPrimaryButton(
                        text = primaryButtonText,
                        style = normal14Text700,
                        start = 5.dp,
                        end = 5.dp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        onPrimaryButtonClick?.invoke()
                    }

                    CurvedPrimaryButton(
                        text = secondaryButtonText,
                        style = normal14Text700,
                        start = 5.dp,
                        end = 5.dp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        onSecondaryButtonClick?.invoke()
                    }

                    CurvedPrimaryButton(
                        text = tertiaryButtonText,
                        style = normal14Text700,
                        start = 5.dp,
                        end = 5.dp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        onTertiaryButtonClick?.invoke()
                    }
                }
            }
        }
    }
}

@Composable
fun TopBottomBarForNegativeScreen(
    navController: NavHostController,
    showTop: Boolean = true,
    showBottom: Boolean = false,
    isSelfScrollable: Boolean = false,
    pageContent: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        // Top bar
        if (showTop) {
            TopBar(navController = navController)
        }
        // Scrollable content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isSelfScrollable) {
                Column(modifier = Modifier.fillMaxSize()) {
                    pageContent()
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    pageContent()
                }
            }
        }
        if (showBottom) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
                    .size(height = 50.dp, width = 200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun TopBar(
    navController: NavHostController,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = { navController.popBackStack() },
    topBarBackgroundColor: Color = appOrange,
    topBarText: String = ""
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(topBarBackgroundColor)
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp, end = 5.dp)
    ) {
        if (showBackButton) {
            BackButton(navController = navController, onClick = onBackClick)
        } else {
            Spacer(modifier = Modifier.width(40.dp))
        }

        StartingText(
            text = topBarText,
            style = if (topBarBackgroundColor == appOrange) normal16Text700 else normal18Text700,
            textColor = if (topBarBackgroundColor == appOrange) appWhite else appBlack,
            start = 5.dp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun ScreenWithHamburger(
    modifier: Modifier = Modifier,
    isSelfScrollable: Boolean = false,
    navController: NavHostController = rememberNavController(),
    topBarText: String = "",
    pageContent: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        ModalDrawer(
            drawerState = drawerState,
            drawerBackgroundColor = Color.White,
            drawerContent = {
                SideMenuContent(coroutineScope, drawerState, navController)
            }
        ) {
            Scaffold(
                topBar = {
                    TopBarWithMenu(
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                        topBarText = topBarText
                    )
                }
            ) { paddingValues ->
                if (isSelfScrollable) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        pageContent()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        pageContent()
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarWithMenu(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    topBarBackgroundColor: Color = appOrange,
    topBarText: String = ""
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(topBarBackgroundColor)
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.hamburger_menu),
            contentDescription = stringResource(id = R.string.hamburger_menu),
            modifier = Modifier
                .clickable { openDrawer(coroutineScope, drawerState) }
                .padding(top = 16.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
                .size(width = 30.dp, height = 20.dp)

        )

        StartingText(
            text = topBarText,
            style = if (topBarBackgroundColor == appOrange) normal16Text700 else normal18Text700,
            textColor = if (topBarBackgroundColor == appOrange) appWhite else appBlack,
            start = 5.dp
        )
    }
}

@Composable
fun SideBarLayout(
    userName: String,
    contact: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    pageContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box {
//            Box {
            HeaderCard {
                SideMenuProfileCard(
                    userName = userName,
                    contact = "+91 $contact",
                    navController = rememberNavController(),
                    onBackClick = { onBackClick?.let { onBackClick() } }
                )
            }
//            }
            Box(modifier = Modifier.padding(top = 230.dp)) {
                HeaderCard(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp,
                    start = 10.dp,
                    end = 10.dp,
                    cardColor = appWhite,
                    borderColor = appWhite
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        pageContent()
                    }
                }
            }
        }
    }
}
