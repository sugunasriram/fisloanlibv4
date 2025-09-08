package com.github.sugunasriram.fisloanlibv4.fiscode.appBridge

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.LoanLib
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.AppScreens
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.LaunchScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToDownPaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.InAppUpdateScreen
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class AppBridgeManager(private val activity: ComponentActivity) {


    var updateCompleted = mutableStateOf(false)
    var showDialog = mutableStateOf(true)
    var verifySessionInvoked = false
    private val context: Context = activity.applicationContext

    @Composable
    fun RenderContent(intent: Intent?, navController: NavController) {
//        val personalDetails = intent?.getSerializableExtra("personalDetails") as? LoanLib.PersonalDetails
//        val productDetails = intent?.getSerializableExtra("productDetails") as? LoanLib.ProductDetails
        val sessionDetails = intent?.getSerializableExtra("sessionDetails") as? LoanLib.SessionDetails

        val viewModel: IncomingIntentViewModel = viewModel()
        val isVerifySessionChecking by viewModel.isVerifySessionChecking.collectAsState()
        val isVerifySessionSuccess by viewModel.isVerifySessionSuccess.collectAsState()

        val verifySessionResponse by viewModel.verifySessionResponse.collectAsState()


        val showInternetScreen by viewModel.showInternetScreen.observeAsState(false)
        val showTimeOutScreen by viewModel.showTimeOutScreen.observeAsState(false)
        val showServerIssueScreen by viewModel.showServerIssueScreen.observeAsState(false)
        val unexpectedErrorScreen by viewModel.unexpectedError.observeAsState(false)
        val unAuthorizedUser by viewModel.unAuthorizedUser.observeAsState(false)

        // Log received data
//        personalDetails?.let { Log.d("AppBridge", "Received personalDetails: $it") }
//        productDetails?.let { Log.d("AppBridge", "Received productDetails: $it") }
        sessionDetails?.let { Log.d("AppBridge", "Received sessionDetails: $it") }

//        if (showDialog.value) {
//            AlertDialog(
//                onDismissRequest = { showDialog.value = false },
//                title = { Text("Details") },
//                text = {
//                    Column {
//                        Text("Session Details: $sessionDetails")
//                        Text("Personal Details: $personalDetails")
//                        Text("Product Details: $productDetails")
//                    }
//                },
//                confirmButton = {
//                    Button(onClick = { showDialog.value = false }) {
//                        Text("OK")
//                    }
//                }
//            )
//        }

        when {
            showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController = rememberNavController())
            showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController = rememberNavController())
            showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController = rememberNavController())
            unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController = rememberNavController())
            unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController = rememberNavController())
            else -> {
                sessionDetails?.sessionId?.let { sessionId ->
                    Log.d("fisloanone", "Session ID: $sessionId")


                    if (!verifySessionInvoked) {
//                        verifySessionInvoked = true
//                        Log.d("fisloanone", "verifySession API : $sessionId")
//
//                        viewModel.verifySessionApi(sessionId, context)

                        val orderId = sessionDetails.loanId
                        val fromFlow = "Purchase Finance"
                        val fromScreen = "PURCHASE_FINANCE"

                        val route = "${AppScreens.RepaymentScheduleScreen.route}/$orderId/$fromFlow/$fromScreen"

                        Log.d("Sugu", "Navigating to: $route")
                        navController.navigate(route)  {
                            launchSingleTop = true
                        }
                    }

                    when {
                        isVerifySessionChecking -> {
                            Log.d("AppBridgeManager", "VerifySession in Progress")
                            CenterProgress()


                        }

                        isVerifySessionSuccess && verifySessionResponse != null -> {
                            Log.d("AppBridgeManager", "VerifySession Done")
                            // Safely read loanId from the response
                            val loanId = verifySessionResponse?.data?.sessionData?.loanId

                            LaunchedEffect(Unit) {
                                if (!loanId.isNullOrBlank()) {
                                    // Case 1: loanId exists → go to DownPaymentScreen
                                    navController.navigate(AppScreens.RepaymentScheduleScreen.route) {
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(AppScreens.DownPaymentScreen.route)
                                    navController.getBackStackEntry(AppScreens.DownPaymentScreen.route)
                                        .savedStateHandle
//                                    .set("verifySessionResponse", verifySessionResponse)
                                        .set(
                                            "verifySessionResponse",
                                            Json.encodeToString<VerifySessionResponse?>(
                                                verifySessionResponse
                                            )
                                        )
                                }
                            }
                        }
                        verifySessionInvoked &&
                                !isVerifySessionChecking &&
                                !isVerifySessionSuccess &&
                                verifySessionResponse == null -> {
                            // We are here only if API is finished and failed → use mock or error
//                            Log.d("AppBridgeManager", "Failed to get verifySessionResponse, using mock data")
//                            Log.d("Sugu", "verifySessionInvoked : " +
//                                    "$verifySessionInvoked, isVerifySessionChecking : " +
//                                    "$isVerifySessionChecking, " +"isVerifySessionSuccess : $isVerifySessionSuccess, ")
//                            var verifySessionResponse1 = "{\n" +
//                                    "    \"statusCode\": 201,\n" +
//                                    "    \"status\": true,\n" +
//                                    "    \"message\": \"Session Retrived Successfully\",\n" +
//                                    "    \"data\": {\n" +
//                                    "        \"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxMDE2NzA0LCJleHAiOjE3NTE2MjE1MDR9.WpYrU6AvEsubAarJyna2rkHFWGK3oM208RPxORKBPfk\",\n" +
//                                    "        \"sessionId\": \"83f29f24-704d-529f-a3b4-4a5560cd2c70\",\n" +
//                                    "        \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxNDQyNDUxLCJleHAiOjE3NTE0NDI2MzF9.ziPqEqA9eFr_Co-B1b6mwYMKgzgTjjrFR8lYeUIilME\",\n" +
//                                    "        \"sseId\": \"a41bb04e18a5545395ba6cba4e34607c\",\n" +
//                                    "        \"securityKey\": \"110e5c0419135e22814945c143ab9fda\",\n" +
//                                    "        \"sessionData\": {\n" +
//                                    "            \"downPayment\": 0,\n" +
//                                    "            \"productId\": \"2a9a7100-bf22-5858-83f3-62e65c2e3a8c\",\n" +
//                                    "            \"merchantPAN\": null,\n" +
//                                    "            \"merchantBankAccount\": null,\n" +
//                                    "            \"merchantGST\": null,\n" +
//                                    "            \"merchantAccountHolderName\": null,\n" +
//                                    "            \"merchantIfscCode\": null,\n" +
//                                    "            \"productBrand\": null,\n" +
//                                    "            \"productCategory\": \"Mobile Phone\",\n" +
//                                    "            \"productSKUID\": \"47af53c0-0add-5c49-9365-8f42fafa18fe\",\n" +
//                                    "            \"productReturnWindow\": \"P7D\",\n" +
//                                    "            \"productModel\": null,\n" +
//                                    "            \"productSellingPrice\": \"90000\",\n" +
//                                    "            \"productCancellable\": true,\n" +
//                                    "            \"productReturnable\": true,\n" +
//                                    "            \"productName\": \"VRIDDHI\",\n" +
//                                    "            \"productMrpPrice\": 100000,\n" +
//                                    "            \"productSymbol\": \"https://storage.googleapis.com/nslive/62bab198-9391-5be4-b672-e78a6a299a1f/raw/1742808735478_1742808733756.png\",\n" +
//                                    "            \"productQuantity\": \"1\"\n" +
//                                    "        },\n" +
//                                    "        \"sessionType\": \"FIS_PF\"\n" +
//                                    "    }\n" +
//                                    "}"
//                            LaunchedEffect(Unit) {
//                                navController.navigate(AppScreens.DownPaymentScreen.route)
//                                navController.getBackStackEntry(AppScreens.DownPaymentScreen.route)
//                                    .savedStateHandle
//                                    .set("verifySessionResponse", verifySessionResponse1)
//                            }
                        }
                    }
                } ?: run {
                    if (updateCompleted.value) {
                        Log.d("fisloanone", "No Session ID found")

                        if (!verifySessionInvoked) {
                            Log.d("fisloanone", "Launch Splash screen")

//                            LaunchScreen(AppScreens.SplashScreen.route)
                            val fromFlow = "Purchase_finance"
                            LaunchScreen("${AppScreens.SplashScreen.route}/${fromFlow}")
                        }
                    } else {
                        Log.d("fisloanone", "InAppUpdateScreen")

                        InAppUpdateScreen(activity) {
                            updateCompleted.value = true
                        }
                    }
                }
            }
        }
    }
}
