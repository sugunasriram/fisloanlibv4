package com.github.sugunasriram.fisloanlibv4.fiscode.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateKycScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LoanAgreement
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal36Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.semiBold20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.StatusChip
import kotlinx.coroutines.delay

@SuppressLint("ResourceType")
@Composable
fun LoaderAnimation(
    text: String = stringResource(id = R.string.we_are_currently_processing),
    updatedText: String = stringResource(id = R.string.we_are_currently_processing),
    delayInMillis: Long = 15000,
    @DrawableRes image: Int = R.raw.generating_best_offers,
    @DrawableRes updatedImage: Int = R.raw.generating_best_offers,
    showTimer: Boolean = false,
    navController: NavHostController
) {
    var sizeDepends = true
    var currentText by remember { mutableStateOf(text) }
    var currentImage by remember { mutableStateOf(image) }

    LaunchedEffect(Unit) {
        delay(delayInMillis)
        sizeDepends = false
        currentText = updatedText
        currentImage = updatedImage
    }
    BackHandler { navigateApplyByCategoryScreen(navController) }

    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(currentImage)
    )
    val progressAnimation by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f // Set the animation speed to 0.5
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showTimer) {
            IncrementTimer(navController = navController)
//            CircularCountdownTimer()
        }

        LottieAnimation(
            composition = compositionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(height = 500.dp, width = 300.dp)
        )
        Text(
            text = currentText,
            style = semiBold20Text500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                modifier = Modifier.size(height = 50.dp, width = 200.dp)
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun AnimationLoader(
    delayInMillis: Long = 5000,
    @DrawableRes image: Int = R.raw.fetching_account_details,
    id: String,
    transactionId: String,
    navController: NavHostController,
    fromFlow: String,
    loanAgreementURL: String
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        delay(delayInMillis)
        if(fromFlow.equals("Personal Loan", ignoreCase = true)){
            navigateToBankDetailsScreen(
                navController = navController,
                id = id,
                fromFlow = fromFlow,
                closeCurrent = false
            )
        } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
            navigateToLoanAgreementScreen(
                navController = navController,
                url = loanAgreementURL,
                transactionId = transactionId,
                id = id,
                fromFlow = fromFlow
            )
        }

//        navigateToLoanProcessScreen(
//            navController = navController, transactionId = transactionId, statusId = 4,
//            responseItem = "No need ResponseItem",
//            offerId = id, fromFlow = fromFlow
//        )
    }

    val compotionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes( image))
    val progressAnimation by animateLottieCompositionAsState(
        compotionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = compotionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(width = 300.dp, height = 500.dp)
        )
        Text(
            text = stringResource(id =  R.string.identifying_account_details_for_loan_disbursement),
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            style = semiBold20Text500,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                Modifier.size(height = 50.dp, width = 200.dp)
            )
        }
    }

    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                navigateApplyByCategoryScreen(navController = navController)
                navigateToFISExitScreen(navController, loanId="1234")
            }
        }
    }

    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(callback)
        onDispose { callback.remove() }
    }
}
@SuppressLint("ResourceType")
@Composable
fun LoanAgreementAnimationLoader(
    delayInMillis: Long = 5000,
    @DrawableRes image: Int = R.raw.sign_loan_agreement,
    transactionId:String,
    id: String,
    formUrl: String,
    navController: NavHostController,
    fromFlow: String,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        delay(delayInMillis)
        navigateToLoanAgreementScreen(
            navController = navController,
            transactionId = transactionId,
            id = id,
            fromFlow = fromFlow,
            url = formUrl,
        )

    }

    val compotionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(image))
    val progressAnimation by animateLottieCompositionAsState(
        compotionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = compotionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(width = 300.dp, height = 500.dp)
        )
        Text(
            text = stringResource(id =  R.string.generating_loan_agreement_for_signing),
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            style = semiBold20Text500,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                Modifier.size(height = 50.dp, width = 200.dp)
            )
        }
    }

    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                navigateApplyByCategoryScreen(navController = navController)
                navigateToFISExitScreen(navController, loanId="1234")
            }
        }
    }

    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(callback)
        onDispose { callback.remove() }
    }
}

@SuppressLint("ResourceType")
@Composable
fun KycAnimation(
    text: String = stringResource(id = R.string.initiating_kyc_verification),
    delayInMillis: Long = 5000,
    @DrawableRes image: Int = R.raw.initiating_kyc_verification,
    navController: NavHostController,
    transactionId: String,
    offerId: String,
    responseItem: String,
    fromFlow: String
) {
//    BackHandler { navigateApplyByCategoryScreen(navController) }
    BackHandler { navigateToFISExitScreen(navController, loanId="1234") }

    LaunchedEffect(Unit) {
        delay(delayInMillis)
        if(fromFlow.equals("Personal Loan", ignoreCase = true)){
            navigateKycScreen(
                navController = navController,
                transactionId = transactionId,
                url = responseItem,
                id =
                offerId,
                fromFlow = fromFlow
            )
        } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
            navigateToPfKycWebViewScreen(
                navController = navController,
                transactionId = transactionId,
                kycUrl = responseItem,
                offerId = offerId,
                fromScreen = "2",
                fromFlow = fromFlow
            )
        }
    }
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(image)
    )
    val progressAnimation by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = compositionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(height = 450.dp, width = 300.dp)
        )
        MultiStyleText(
            "Initiating ",
            appBlack,
            "KYC Verification",
            appOrange,
            semiBold20Text500,
            semiBold20Text500,
            start = 0.dp,
            arrangement = Arrangement.Center
        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp, vertical = 20.dp)
                .height(4.dp),
            color = appOrange
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                Modifier.size(height = 50.dp, width = 200.dp)
            )
        }
    }
}

@SuppressLint("ResourceType")
@Preview(showBackground = true)
@Composable
fun PreviewAgreementAnimation() {
//    AnimationLoader(id="",transactionId="",navController= rememberNavController(),fromFlow="",)
//    ProcessingAnimation(text = "Processing Please Wait...", image = R.raw.we_are_currently_processing_hour_glass)//blue
//    ProcessingAnimation(
//        text = stringResource(id = R.string.submitting_bank_details),
//        image = R.raw.submitting_bank_details
//    )
//    ProcessingAnimation()
//    CircularCountdownTimer()
//    LoaderAnimation(
//        text = stringResource(R.string.generating_account_aggregator),
//        updatedText = stringResource(id = R.string.generating_best_offers),
//        image = R.raw.generating_aa_consent,
//        showTimer = true
//    )
//    KycAnimation(
//        navController = rememberNavController(),
//        transactionId ="",
//        offerId ="",
//        responseItem = "",
//        fromFlow = ""
//    )
}

@SuppressLint("ResourceType")
@Composable
fun ProcessingAnimation(
    text: String = stringResource(id = R.string.please_sign_loan_agreement),
    @DrawableRes image: Int = R.raw.sign_loan_agreement
) {
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(image)
    )
    val progressAnimation by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = compositionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(width = 250.dp, height = 500.dp)
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            style = semiBold20Text500,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ondc_icon),
                contentDescription = stringResource(id = R.string.ondc_icon),
                Modifier.size(height = 50.dp, width = 200.dp)
            )
        }
    }
}

@Composable
fun LoanDisburseAnimator() {
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.disburse_gif)
    )
    val progressAnimation by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
        LottieAnimation(
            composition = compositionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun ForeClosureAnimator() {
    val compotionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.force_closure_gif)
    )
    val progressAnimation by animateLottieCompositionAsState(
        compotionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
        LottieAnimation(
            composition = compotionResult.value,
            progress = progressAnimation,
            modifier = Modifier.size(120.dp)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CircularCountdownTimer(
    maxMinutes: Int = 1,
    onTimerEnd: () -> Unit = {}
) {
    var totalSeconds by remember { mutableStateOf(maxMinutes * 60) }
    val progress by animateFloatAsState(
        targetValue = totalSeconds.toFloat() / (maxMinutes * 60).toFloat(),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "Timer Animation"
    )

    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    LaunchedEffect(totalSeconds) {
        if (totalSeconds > 0) {
            delay(1000L)
            totalSeconds--
        } else {
            onTimerEnd()
        }
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val inset = strokeWidth / 2

            val arcSize = Size(
                width = size.width - strokeWidth,
                height = size.height - strokeWidth
            )
            drawArc(
                color = appGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = appOrange,
                startAngle = -90f,
                sweepAngle = -360 * progress,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}

@Composable
fun IncrementTimer(maxMinutes: Int = 5, navController: NavHostController) {
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(maxMinutes) {
        while (minutes < maxMinutes) {
            delay(1000L)
            seconds++
            if (seconds == 60) {
                minutes++
                seconds = 0
            }
        }
        if (minutes >= maxMinutes) {
            Log.d("IncrementTimer", "Timer finished")
//            navigateApplyByCategoryScreen(navController)
            navigateToFISExitScreen(navController, loanId="1234")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(20.dp))
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = normal36Text500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewIncrementTimer() {
    StatusChip(
        statusText = "ADD ISSUE +",
        backGroundColor = Color.Transparent,
        borderColor = errorRed,
        textColor = errorRed,
        cardWidth = 1.dp,
        textStyle = normal18Text500,
        modifier = Modifier.padding(top = 5.dp, end = 0.dp)
    )
//    IncrementTimer(maxMinutes = 2)
}
