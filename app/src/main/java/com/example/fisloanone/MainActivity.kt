package com.example.fisloanone

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.sugunasriram.fisloanlibv4.LoanLib

class MainActivity : ComponentActivity() {

    fun PopulateSessionDetails(): LoanLib.SessionDetails {
        return LoanLib.SessionDetails(
            sessionId = "ee31146f-b654-512d-bca0-7879df316fec",
            loanId = ""
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoanLib.LaunchFISAppWithParamsAndCallback(
            context = this,
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
