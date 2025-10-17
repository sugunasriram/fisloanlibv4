package com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TopBottomBarForNegativeScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal36Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.sugunasriram.fisloanlibv4.LoanLib
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFDeleteUserBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance.PurchaseFinanceViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.interestRate
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.loanAmount
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.tenure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun FISExitCofirmationScreen(
    navController: NavHostController,
    loanId: String="", // <-- pass the real loanId in your BackHandler -> navigate to this screen
    onAbortSuccessNavigate: () -> Unit = { navigateApplyByCategoryScreen(navController) },
    onContinueClick: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()

    // If your Compose version needs an initial, use it:
    val createState by loanAgreementViewModel.createSessionState
        .collectAsState(initial = LoanAgreementViewModel.CreateSessionUiState.Idle)

    val inProgress = createState is LoanAgreementViewModel.CreateSessionUiState.Loading

    val downpaymentAmountValue = remember { mutableStateOf<String?>(null) }
    val loanTenureValue = remember { mutableStateOf<String?>(null) }

    val autoAborted = remember { mutableStateOf(false) }
    val forceLoader = remember { mutableStateOf(false) }

    val purchaseFinanceViewModel: PurchaseFinanceViewModel = viewModel()


    // Load values
    LaunchedEffect(Unit) {
        downpaymentAmountValue.value = TokenManager.read("downpaymentAmount")
        loanTenureValue.value = TokenManager.read("pfloanTenure")
        Log.d(
            "LoanDisbursementScreen",
            "Sugu downpaymentAmountValue: ${downpaymentAmountValue.value}, " +
                    "loanTenure:{${loanTenureValue.value}}"
        )
    }

    // --- helper to perform the ABORT action (same as tapping the ABORT button) ---
    fun triggerAbort() {
        if (!inProgress) {
            val sanitizedLoanId = loanId.takeUnless { it == "1234" }.orEmpty()
            loanAgreementViewModel.createPfSession(sanitizedLoanId, context)

            CoroutineScope(Dispatchers.Main).launch {
                val mobileNumber = TokenManager.read("mobileNumber")
                purchaseFinanceViewModel.pFDeleteUser(
                    context = context,
                    deleteUserBodyModel = PFDeleteUserBodyModel(
                        loanType = "PURCHASE_FINANCE",
                        mobileNumber = mobileNumber
                    ),
                )
            }
        }
    }

    // Auto abort for loanId = 4321
    LaunchedEffect(loanId) {
        if (loanId == "4321" && !autoAborted.value) {
            autoAborted.value = true
            forceLoader.value = true
            triggerAbort()
        }
    }

    // React to VM state changes
    LaunchedEffect(createState) {
        when (val s = createState) {
            is LoanAgreementViewModel.CreateSessionUiState.Success -> {
                onAbortSuccessNavigate()
                val sessionId = s.sessionId
                val downpaymentAmountVal = downpaymentAmountValue.value?.toIntOrNull() ?: 0
                val loanTenureVal = loanTenureValue.value?.toIntOrNull() ?: 0

                val isDummyLoan = (loanId == "1234" || loanId == "4321")
                val finalLoanAmount = if (isDummyLoan) 0.0 else loanAmount?.toDoubleOrNull() ?: 0.0
                val finalInterestRate = if (isDummyLoan) 0.0 else interestRate?.toDoubleOrNull() ?: 0.0
                val finalTenure = if (isDummyLoan) 0 else loanTenureVal

                val details = LoanLib.LoanDetails(
                    sessionId = sessionId,
                    interestRate = finalInterestRate,
                    loanAmount = finalLoanAmount,
                    tenure = finalTenure,
                    downpaymentAmount = downpaymentAmountVal
                )
                LoanLib.callback?.invoke(details)
                (context as? Activity)?.finish()
            }
            is LoanAgreementViewModel.CreateSessionUiState.Error -> {
//                Toast.makeText(context, s.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.d("FISExitCofirmationScreen", s.message ?: "Something went wrong")
            }
            else -> Unit
        }
    }

    if (forceLoader.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.processing_please_wait),
                    style = normal14Text700,
                    color = appBlack
                )
            }
        }
    }
    else {
        TopBottomBarForNegativeScreen(
            showTop = false,
            showBottom = true,
            navController = navController
        ) {
            StartingText(
                text = stringResource(R.string.exit_loan_process),
                textColor = errorRed,
                start = 30.dp, end = 30.dp, top = 60.dp, bottom = 15.dp,
                style = normal36Text700,
                alignment = Alignment.Center
            )

            Image(
                painter = painterResource(id = R.drawable.error_no_loan_offers_available),
                contentDescription = "loan Status",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().size(250.dp)
            )

            RegisterText(
                text = stringResource(id = R.string.your_loan_in_progress),
                style = normal14Text700,
                textColor = hintGray,
                top = 10.dp, bottom = 25.dp
            )

            // --- Buttons in one row ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // CONTINUE (stay)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !inProgress) { onContinueClick() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ClickableText(
                        text = stringResource(id = R.string.continue_),
                        style = normal20Text700
                    ) { if (!inProgress) onContinueClick() }
                }

                // ABORT (leave) -> call createPfSession; UI will navigate on Success
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !inProgress) { triggerAbort() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (inProgress) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        ClickableText(
                            text = stringResource(id = R.string.abort),
                            style = normal20Text700
                        ) { triggerAbort() }
                    }
                }
            }
        }

        // (Optional) full-screen dim overlay loader while inProgress
        if (inProgress) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.processing_please_wait),
                        style = normal14Text700,
                        color = appBlack
                    )
                }
            }
        }
    }
}



//@Preview
//@Composable
//private fun FISExitCofirmationScreenPreview() {
//    Surface {
//        FISExitCofirmationScreen(rememberNavController(),"", {},{})
//    }
//}
