package com.example.fisloanone

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.sugunasriram.fisloanlibv4.LoanLib
import com.github.sugunasriram.fisloanlibv4.LoanLib.SessionDetails
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.loanId

class MainActivity : ComponentActivity() {

    private fun PopulateSessionDetails(): LoanLib.SessionDetails {
        return SessionDetails(
//            sessionId = "83f29f24-704d-529f-a3b4-4a5560cd2c70"
//            sessionId = "289ee575-ce4c-58b8-98be-39204d680108",
            sessionId = "32062c54-4ae9-5150-998a-a81579850321",
//            loanId = "87345870-3b2a-442d-9ece-309afcc0f002"
//            loanId = "ee723d1c-9515-4a45-bc57-39b4c84da28e"
//            loanId = "a022ec36-3db1-4871-8aef-b2ae3ceb2292"
//            sessionId = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            AndroidLibraryTheme {
//                Box(modifier = Modifier.fillMaxSize()) {
//
//                }
//            }

        // 2. Only Image
        // LoanLib.ImagePreview()

//        }
        // 3. Image with 2 buttons - Working
        // LoanLib.DisplayImageAndTwoButtons(context = this)

        // LoanLib.LaunchThirdScreen(context = this)

        // 1.0.13
//        LoanLib.LaunchFISAppWithParams(
//            context = this,
//            personalDetails = PopulatePersonalDetails(),
//            productDetails = PopulateProductDetails())

        LoanLib.LaunchFISAppWithParamsAndCallback(
            context = this,
//            personalDetails = PopulatePersonalDetails(),
//            productDetails = PopulateProductDetails(),
            sessionDetails = PopulateSessionDetails()
        ) { loanDetails ->
            // Handle the callback with loanDetails
            println("SessionId: ${loanDetails.sessionId}")
            println("Loan Amount: ${loanDetails.loanAmount}")
            println("Interest Rate: ${loanDetails.interestRate}")
            println("Tenure: ${loanDetails.tenure}")
        }
    }
}
