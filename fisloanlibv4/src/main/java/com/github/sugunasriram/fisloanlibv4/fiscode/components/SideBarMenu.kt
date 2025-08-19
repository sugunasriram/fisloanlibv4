package com.github.sugunasriram.fisloanlibv4.fiscode.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAboutUsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToContactUsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToIssueListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLanguageScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanStatusScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPrePaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPrivacyPolicyScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToTermsConditionsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToUpdateProfileScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.textBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.content.Context
import android.util.Log
import androidx.compose.ui.text.TextStyle
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import kotlin.math.exp

fun openDrawer(coroutineScope: CoroutineScope, drawerState: DrawerState) {
    coroutineScope.launch {
        drawerState.open()
    }
}

fun closeDrawer(coroutineScope: CoroutineScope, drawerState: DrawerState) {
    coroutineScope.launch {
        drawerState.close()
    }
}

@Composable
fun SideMenuTextButton(
    title: String,
    titleStyle:TextStyle = normal20Text400,
    isSubMenu: Boolean = false,
    painter: Painter = painterResource(id = R.drawable.about),
    onClick: () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = title.lowercase(),
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = title,
                    style = titleStyle,
                    color = appBlack,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            if (!isSubMenu) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_forward_gray),
                    contentDescription = title.lowercase(),
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        if (title != stringResource(id = R.string.logout) && title != stringResource(
                id = R.string.loan_status
            ) && !isSubMenu
        ) {
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSideMenuContent() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val navController = rememberNavController()

    SideMenuContent(
        coroutineScope = coroutineScope,
        drawerState = drawerState,
        navController = navController
    )
}

@SuppressLint("SuspiciousIndentation", "RememberReturnType")
@Composable
fun SideMenuContent(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController
) {
    val registerViewModel: RegisterViewModel = viewModel()
    val inProgress by registerViewModel.inProgress.collectAsState()
    val isCompleted by registerViewModel.isCompleted.collectAsState()
    val getUserResponse by registerViewModel.getUserResponse.collectAsState()

    val loanAgreementViewModel : LoanAgreementViewModel = viewModel()
    val plLoanListLoading by loanAgreementViewModel.plLoanListLoading.collectAsState()
    val plLoanListLoaded by loanAgreementViewModel.plLoanListLoaded.collectAsState()
    val pfLoanListLoading by loanAgreementViewModel.pfLoanListLoading.collectAsState()
    val pfLoanListLoaded by loanAgreementViewModel.loanListLoaded.collectAsState()
    val personalLoanList by loanAgreementViewModel.personalLoanList.collectAsState()
    val purchaseFinanceList by loanAgreementViewModel.purchaseFinanceList.collectAsState()

    var showLogoutConfirmationPopUp by remember { mutableStateOf(false) }
    var showAboutMenuPopUp by remember { mutableStateOf(false) }
    var showSettingsMenuPopUp by remember { mutableStateOf(false) }
    var expandLoanStatus by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val fullName = listOfNotNull(
        getUserResponse?.data?.firstName?.takeIf { it.isNotBlank() },
        getUserResponse?.data?.lastName?.takeIf { it.isNotBlank() }
    ).joinToString(" ")

    SideBarLayout(
        userName = fullName.ifBlank { "" },
        contact = getUserResponse?.data?.mobileNumber.orEmpty(),
        onBackClick = { closeDrawer(coroutineScope, drawerState) }
    ) {
        SideMenuTextButton(
            title = stringResource(id = R.string.update_profile),
            painter = painterResource(id = R.drawable.update_profile),
            onClick = {
                closeDrawer(coroutineScope, drawerState)
                navigateToUpdateProfileScreen(navController, fromFlow = "SideBar")
            }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.loan_status),
            titleStyle = if (expandLoanStatus) normal20Text700 else normal20Text400,
            painter = painterResource(id = R.drawable.loan_status),
            onClick = {
                // Trigger API calls only when expanding for the first time
                if (!expandLoanStatus) {
                    loanAgreementViewModel.completeLoanList(context, "PERSONAL_LOAN")
                    loanAgreementViewModel.completeLoanList(context, "PURCHASE_FINANCE")
                }
                expandLoanStatus = !expandLoanStatus
            }
        )

        if (expandLoanStatus) {
            val allLoaded = plLoanListLoaded && pfLoanListLoaded
            val allEmpty = personalLoanList?.data.isNullOrEmpty() &&
                    purchaseFinanceList?.data.isNullOrEmpty()

            if (allLoaded && allEmpty) {
                expandLoanStatus = false
                CommonMethods().toastMessage(context, "No loan offers available")
            }
            // PERSONAL_LOAN
            when {
                plLoanListLoading -> CenterProgress()

                plLoanListLoaded && !personalLoanList?.data.isNullOrEmpty() -> {
                    StartingText(
                        text = stringResource(id = R.string.personal_loan),
                        textColor = appOrange,
                        style = normal18Text500,
                        modifier = Modifier.clickable {
                            closeDrawer(coroutineScope, drawerState)
                            navigateToLoanStatusScreen(navController, "PERSONAL_LOAN")
                        },
                        start = 45.dp,
                        top = 4.dp
                    )
                }
            }

            // PURCHASE_FINANCE
            when {
                pfLoanListLoading -> CenterProgress()
                pfLoanListLoaded && !purchaseFinanceList?.data.isNullOrEmpty() -> {
                    StartingText(
                        text = stringResource(id = R.string.purchase_finance),
                        textColor = appOrange,
                        style = normal18Text500,
                        modifier = Modifier.clickable {
                            closeDrawer(coroutineScope, drawerState)
                            navigateToLoanStatusScreen(navController, "PURCHASE_FINANCE")
                        },
                        start = 45.dp,
                        top = 4.dp
                    )
                }
            }
        }

        HorizontalDivider(top = 0.dp)
        SideMenuTextButton(
            title = stringResource(id = R.string.contact_us),
            painter = painterResource(id = R.drawable.contact),
            onClick = {
                closeDrawer(coroutineScope, drawerState)
                coroutineScope.launch {
                    TokenManager.read("email").takeIf { it != "null" }?.let {
                        navigateToContactUsScreen(navController)
                    } ?: run {
                        navigateToUpdateProfileScreen(navController, fromFlow = "SideBar")
                    }
                }
            }
        )

//        HorizontalDivider(top = 0.dp)
        SideMenuTextButton(
            title = stringResource(id = R.string.my_issues),
            painter = painterResource(id = R.drawable.my_issues),
            onClick = {
                closeDrawer(coroutineScope, drawerState)
                navigateToIssueListScreen(
                    navController = navController,
                    orderId = "12345",
                    fromFlow = "Both",
                    providerId = "12345",
                    loanState = "No Need",
                    fromScreen = "HAMBURGER"
                )
            }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.notification),
            painter = painterResource(id = R.drawable.notification),
            onClick = {
                CommonMethods().toastMessage(context = context, toastMsg = context.getString(R.string.feature_supported_in_future))
                closeDrawer(coroutineScope, drawerState)
            }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.pre_payment),
            painter = painterResource(id = R.drawable.pre_payment),
            onClick = {
                closeDrawer(coroutineScope, drawerState)
                navigateToPrePaymentScreen(navController)
            }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.settings),
            painter = painterResource(id = R.drawable.settings),
            onClick = { showSettingsMenuPopUp = true }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.share_app),
            painter = painterResource(id = R.drawable.share),
            onClick = {
                shareApp(context)
                closeDrawer(coroutineScope, drawerState)
            }
        )
        SideMenuTextButton(
            title = stringResource(id = R.string.about),
            painter = painterResource(id = R.drawable.about),
            onClick = { showAboutMenuPopUp = true }
        )
        SideMenuTextButton(
            stringResource(id = R.string.logout),
            painter = painterResource(id = R.drawable.logout),
            onClick = { showLogoutConfirmationPopUp = true }
        )
    }
    if (showLogoutConfirmationPopUp) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirmationPopUp = false },
            confirmButton = {
                CurvedPrimaryButton(
                    text = stringResource(id = R.string.yes),
                    start = 15.dp,
                    end = 15.dp,
                    top = 5.dp,
                    bottom = 5.dp
                ) {
                    showLogoutConfirmationPopUp = false
                    coroutineScope.launch {
                        val refreshToken =
                            TokenManager.read("refreshToken")
                        refreshToken?.let {
                            registerViewModel.logout(
                                refreshToken,
                                navController
                            )
                        }
                    }

                    closeDrawer(coroutineScope, drawerState)
                }
            },
            dismissButton = {
                CurvedPrimaryButton(
                    text = stringResource(id = R.string.no),
                    start = 15.dp,
                    end = 15.dp,
                    top = 5.dp,
                    bottom = 5.dp
                ) {
                    showLogoutConfirmationPopUp = false
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.logout_confirmation),
                    style = normal32Text500,
                    modifier = Modifier,
                    color = textBlack
                )
            },
            text = { Text(stringResource(id = R.string.are_you_sure_you_want_to_logout)) },
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                .border(1.dp, appWhite, shape = RoundedCornerShape(8.dp))
        )
    }
    if (showAboutMenuPopUp) {
        SubMenuView(
            onDismiss = { showAboutMenuPopUp = false },
            items = listOf(
                stringResource(id = R.string.terms_and_conditions) to R.drawable.terms_and_conditions,
                stringResource(id = R.string.privacy_policy) to R.drawable.privacy_policy,
                stringResource(id = R.string.about_us) to R.drawable.about_us
            )
        ) { selectedItem ->
            when (selectedItem) {
                "Terms & Conditions" -> navigateToTermsConditionsScreen(navController)
                "Privacy Policy" -> navigateToPrivacyPolicyScreen(navController)
                "About Us" -> navigateToAboutUsScreen(navController)
            }
            closeDrawer(coroutineScope, drawerState)
        }
    }

    if (showSettingsMenuPopUp) {
        SubMenuView(
            position = 230.dp,
            height = 90.dp,
            onDismiss = { showSettingsMenuPopUp = false },
            items = listOf(stringResource(id = R.string.language) to R.drawable.language)
        ) { selectedItem ->
            if (selectedItem == "Language") {
                navigateToLanguageScreen(navController)
                closeDrawer(coroutineScope, drawerState)
            }
        }
    }
}

fun shareApp(context: Context) {
//    val packageName = BuildConfig.APPLICATION_ID
    val packageName = "com.github.sugunasriram.fisloanlibv4.fiscode"

    val appLink = "https://play.google.com/store/apps/details?id=$packageName"

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Nearshop Financial Services app: $appLink")
        type = "text/plain"
    }

    context.startActivity(Intent.createChooser(intent, "Share via"))
}

@Composable
fun ContactUsText(
    text1: String,
    text2: String,
    start: Dp = 40.dp,
    top: Dp = 4.dp,
    bottom: Dp = 0.dp,
    arrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = arrangement,
        modifier = Modifier
            .padding(top = top, start = start, bottom = bottom)
            .fillMaxWidth()
    ) {
        Text(text = text1, color = appBlack, style = normal16Text500)
        Text(
            text = text2,
            color = appOrange,
            style = normal16Text500.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun SubMenuView(
    position: Dp = 80.dp,
    height: Dp = 170.dp,
    onDismiss: () -> Unit,
    items: List<Pair<String, Int>>,
    onItemClick: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = onDismiss,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(bottom = position),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .height(height)
                    .padding(15.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                    .background(appWhite)
            ) {
                items.forEach { (title, icon) ->
                    SideMenuTextButton(
                        title = title,
                        isSubMenu = true,
                        painter = painterResource(id = icon),
                        onClick = {
                            onDismiss()
                            onItemClick(title)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SideMenuProfileCard(
    userName: String,
    contact: String,
    displayUserInfo: Boolean = true,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, top = 10.dp)
    ) {
        BackButton(navController = navController, onClick = onBackClick)
        SideMenuProfileDetails(
            userName = userName,
            contact = contact,
            displayUserInfo = displayUserInfo,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(40.dp))
//        Image(
//            painter = painterResource(id = R.drawable.notification_white_icon),
//            contentDescription = "",
//            modifier = Modifier
//                .size(55.dp)
//                .padding(10.dp),
//        )
    }
}

@Composable
fun SideMenuProfileDetails(
    userName: String,
    contact: String,
    displayUserInfo: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(appOrange)
            .padding(bottom = 5.dp, top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_image_icon),
            contentDescription = stringResource(id = R.string.profile_image_icon),
            modifier = Modifier
                .size(120.dp)
                .padding(top = 2.dp, bottom = 5.dp)
        )
        if (displayUserInfo) {
            Text(text = userName, style = normal18Text500, modifier = Modifier, color = appWhite)
            Text(text = contact, style = normal16Text500, modifier = Modifier, color = appWhite)
        } else {
            Text(
                text = stringResource(R.string.my_profile),
                style = normal20Text700,
                modifier = Modifier,
                color = appWhite
            )
        }
    }
}
