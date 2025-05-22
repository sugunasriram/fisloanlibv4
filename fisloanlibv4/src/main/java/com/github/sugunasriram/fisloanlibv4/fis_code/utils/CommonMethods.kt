package com.github.sugunasriram.fisloanlibv4.fis_code.utils

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.NegativeCommonScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.RequestTimeOutScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.UnAuthorizedScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.UnexpectedErrorScreen
import io.ktor.client.features.ResponseException
import io.ktor.client.statement.readText
import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.NumberFormat
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.concurrent.TimeoutException
import java.util.regex.Pattern

class CommonMethods {

    companion object {
        const val BASE_URL = "https://stagingondcfs.jtechnoparks.in/jt-bap"
    }

    private val emailPattern =
        "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"

    private val gstNumberPattern = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][0-9][A-Z][0-9]$"

    /* The password contains at least one digit, one lowercase letter, one uppercase letter, and
    one special character.
    The password doesn't contain any whitespace characters.
    The password is at least 4 characters long.
    */
    private val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

    /* Exactly 5 uppercase letters (A-Z).
        Followed by exactly 4 digits (0-9).
        Followed by exactly 1 uppercase letter (A-Z).
    */
    private val panNumberPattern = "[A-Z]{5}[0-9]{4}[A-Z]"
    private val ifscPattern = "^[A-Z]{4}0[A-Z0-9]{6}\$"

    /***
     5 uppercase letters,
     a hyphen,
     2 uppercase letters,
     another hyphen,
     2 digits,
     another hyphen,
     and finally, 7 digits*/
    private val udyamPattern = "^[A-Z]{5}-[A-Z]{2}-[0-9]{2}-[0-9]{7}\$"

//    fun isValidEmail(email: String?) = email?.let {
//        Pattern.compile(emailPattern).matcher(it).find()
//    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(
            "^(?!.*\\.\\.)(?!.*\\.@)(?!.*@\\.)(?!.*\\s)([a-zA-Z0-9]+[\\w.-]*)@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,10}$"
        )
        return emailRegex.matches(email)
    }

    fun isValidPanNumber(panNumber: String?) = panNumber?.let {
        Pattern.compile(panNumberPattern).matcher(it).find()
    }

    fun isValidIfscCode(ifsc: String?) = ifsc?.let {
        Pattern.compile(ifscPattern).matcher(it).find()
    }

    fun isValidGstNumber(gstNumber: String?) = gstNumber?.let {
        Pattern.compile(gstNumberPattern).matcher(it).find()
    }

    fun isValidUdyamNumber(udyamNumber: String?) = udyamNumber?.let {
        Pattern.compile(udyamPattern).matcher(it).find()
    }

    fun parseErrorMessage(responseBody: String): String {
        // Parse the JSON response body to extract the error message
        // You can use any JSON parsing library like kotlinx.serialization, Gson, etc.
        // Here, I'm assuming responseBody contains JSON like {"error": "Error message"}
        val jsonObject = JSONObject(responseBody)
//        return jsonObject.getString("error")

        /* Extract the first array element of jsonArray and parse to get the value for "message" */
        val dataArray = jsonObject.getJSONArray("data")
        var errorMsg = ""
        for (i in 0 until dataArray.length()) {
            val errorObject = dataArray.getJSONObject(i)
            val errorMessage = errorObject.getString("message")
            val errorDetails = errorObject.getString("details")
            errorMsg = "$errorDetails:$errorMessage\n"
        }

        return errorMsg
    }

    val CAMERA_PERMISSION_REQUEST_CODE = 1001

    fun formatIndianCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        format.maximumFractionDigits = 0
        format.minimumFractionDigits = 0
        format.isGroupingUsed = true

//    val formatter = java.text.DecimalFormat("##,##,##,##0.00")
        return format.format(amount)
    }

    fun formatIndianDoubleCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 0
        format.isGroupingUsed = true

        return format.format(amount)
    }

    fun roundToNearestHundred(value: Float): Float {
        return Math.round(value / 100) * 100.toFloat()
    }

    @Composable
    fun displayFormattedDate(timeStamp: String): String {
        val formattedDate = remember {
            // Parse the timestamp string to a ZonedDateTime object
            val zonedDateTime = ZonedDateTime.parse(timeStamp)

            // Convert to LocalDateTime if needed or format directly
//            val localDateTime = zonedDateTime.toLocalDateTime()
            val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()

            // Define the formatter
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a")

            // Format the LocalDateTime to a string
            localDateTime.format(formatter)
        }
        return formattedDate
    }

    fun numberToWords(number: Long): String {
        if (number == 0L) return "Zero"

        val units = arrayOf(
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
            "Seventeen", "Eighteen", "Nineteen"
        )

        val tens = arrayOf(
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        )

        fun convertChunk(number: Long): String {
            return when {
                number < 20 -> units[number.toInt()]
                number < 100 -> tens[(number / 10).toInt()] + if (number % 10 != 0L) " " + units[(number % 10).toInt()] else ""
                number < 1000 -> units[(number / 100).toInt()] + " Hundred" + if (number % 100 != 0L) {
                    " " + convertChunk(
                        number % 100
                    )
                } else {
                    ""
                }

                else -> throw IllegalArgumentException("Chunk conversion only supports numbers < 1000")
            }
        }

        val crores = number / 10000000
        val lakhs = (number % 10000000) / 100000
        val thousands = (number % 100000) / 1000
        val hundreds = number % 1000

        val crorePart = if (crores > 0) convertChunk(crores) + " Crore " else ""
        val lakhPart = if (lakhs > 0) convertChunk(lakhs) + " Lakh " else ""
        val thousandPart = if (thousands > 0) convertChunk(thousands) + " Thousand " else ""
        val hundredPart = if (hundreds > 0) convertChunk(hundreds) else ""

        return (crorePart + lakhPart + thousandPart + hundredPart).trim()
    }

    @Composable
    fun ShowInternetErrorScreen(navController: NavHostController) {
        NegativeCommonScreen(
            navController = navController,
            errorText = stringResource(id = R.string.unable_to_connect),
            solutionText = stringResource(id = R.string.check_your_internet),
            onClick = { navigateApplyByCategoryScreen(navController) }
        )
    }

    @Composable
    fun ShowTimeOutErrorScreen(navController: NavHostController) {
        RequestTimeOutScreen(navController, onClick = { navigateApplyByCategoryScreen(navController) })
    }

    @Composable
    fun ShowServerIssueErrorScreen(navController: NavHostController) {
        NegativeCommonScreen(
            navController = navController,
            errorText = stringResource(id = R.string.server_temporarily_unavailable),
            solutionText = stringResource(id = R.string.please_try_again_after_sometime),
            onClick = { navigateApplyByCategoryScreen(navController) }
        )
    }

    @Composable
    fun ShowUnexpectedErrorScreen(navController: NavHostController) {
        UnexpectedErrorScreen(navController = navController, onClick = { navigateApplyByCategoryScreen(navController) })
    }

    @Composable
    fun ShowUnAuthorizedErrorScreen(navController: NavHostController) {
        UnAuthorizedScreen(navController, onClick = { navigateSignInPage(navController) })
    }

    @Composable
    fun ShowMiddleLoanErrorScreen(
        navController: NavHostController
    ) {
        UnexpectedErrorScreen(
            navController = navController,
            errorMsg = stringResource(id = R.string.middle_loan_error_message),
            onClick = { navigateApplyByCategoryScreen(navController) }
        )
//        NoResponseFormLenders(navController = navController)
    }

    @Composable
    fun ShowMiddleLoanErrorScreen(
        navController: NavHostController,
        errorMessage: String,
        errorMsgShow: Boolean = false
    ) {
        UnexpectedErrorScreen(
            navController = navController,
            errorMsgShow = errorMsgShow,
            errorText = errorMessage,
            errorMsg = stringResource(id = R.string.middle_loan_error_message),
            onClick = { navigateApplyByCategoryScreen(navController) }
        )
//        NoResponseFormLenders(navController = navController)
    }

    data class RemainingTime(
        val isFuture: Boolean,
        val days: Long,
        val hours: Long,
        val minutes: Long,
        val seconds: Long
    )

    @Composable
    fun timeBufferString(remainingTime: RemainingTime): String {
        val components = mutableListOf<String>()

        if (remainingTime.days > 0) {
            components.add("${remainingTime.days} days")
        }
        if (remainingTime.hours > 0) {
            components.add("${remainingTime.hours} hours")
        }
        if (remainingTime.minutes > 0) {
            components.add("${remainingTime.minutes} minutes")
        }
        if (remainingTime.seconds > 0) {
            components.add("${remainingTime.seconds} seconds")
        }

        return components.joinToString(separator = ", ")
    }

    fun displayFormattedText(originalText: String): String {
        val formattedText = originalText.split("_").joinToString(" ") {
            it.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }
        return formattedText
    }

    @Composable
    fun getRemainingTime(dateString: String): RemainingTime? {
        // Define the date formatter according to the input date format
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        return try {
            // Parse the input date string to a ZonedDateTime
            val inputDate = ZonedDateTime.parse(dateString, formatter)

            // Get the current date in UTC
            val now = ZonedDateTime.now(ZoneId.of("UTC"))

            // Calculate the duration between the input date and now
            val duration = Duration.between(now, inputDate)

            // Determine if the input date is in the future or past
            val absDuration = if (duration.isNegative) duration.negated() else duration

            val isFuture = !duration.isNegative

            // Extract days, hours, minutes, and seconds from the duration
            val days = absDuration.toDays()
            val hours = absDuration.minus(days, ChronoUnit.DAYS).toHours()
            val minutes =
                absDuration.minus(days, ChronoUnit.DAYS).minus(hours, ChronoUnit.HOURS).toMinutes()
            val seconds = absDuration.minus(days, ChronoUnit.DAYS).minus(hours, ChronoUnit.HOURS)
                .minus(minutes, ChronoUnit.MINUTES).seconds

            RemainingTime(isFuture, days, hours, minutes, seconds)
        } catch (e: Exception) {
            null // Return null if parsing fails
        }
    }

    fun toastMessage(context: Context, toastMsg: String) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun HandleErrorScreens(
        navController: NavHostController,
        showInternetScreen: Boolean,
        showTimeOutScreen: Boolean,
        showServerIssueScreen: Boolean,
        unexpectedErrorScreen: Boolean,
        unAuthorizedUser: Boolean = false
    ) {
        when {
            // No internet connection
            showInternetScreen -> {
                NegativeCommonScreen(
                    navController = navController,
                    errorImage = painterResource(id = R.drawable.error_no_internet_image),
                    errorTextTop = 0.dp,
                    showRefreshButton = false,
                    errorText = stringResource(id = R.string.unable_to_connect),
                    solutionText = stringResource(id = R.string.check_your_internet),
                    onClick = { navigateSignInPage(navController) }
                )
            }
            // Request timed out
            showTimeOutScreen -> {
                RequestTimeOutScreen(navController, onClick = { navigateSignInPage(navController) })
            }

            // Server Unavailable
            showServerIssueScreen -> {
                NegativeCommonScreen(
                    navController = navController,
                    errorImage = painterResource(id = R.drawable.error_server_unavailable_image),
                    errorText = stringResource(id = R.string.server_temporarily_unavailable),
                    solutionText = stringResource(id = R.string.please_try_again_after_sometime),
                    onClick = { navigateSignInPage(navController) }
                )
            }

            // oops-something went wrong
            unexpectedErrorScreen -> {
                UnexpectedErrorScreen(navController = navController, onClick = { navigateApplyByCategoryScreen(navController = navController) })
            }
            // unAuthUser
            unAuthorizedUser -> {
                UnAuthorizedScreen(navController = navController, onClick = { navigateSignInPage(navController) })
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewHandleErrorScreens() {
        val navController = rememberNavController()
        HandleErrorScreens(
            navController = navController,
            showInternetScreen = false, // done
            showTimeOutScreen = true, // done
            showServerIssueScreen = false, // done
            unexpectedErrorScreen = false, // done
            unAuthorizedUser = false // done
        )
    }

    fun openLink(context: Context, urlToOpen: String?) {
        urlToOpen?.let {
            var url = it
            if (!it.startsWith("http://") && !it.startsWith("https://")) {
                url = "http://$url"
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }

    fun setFromFlow(fromFlow: String): String {
        val loanType = if (fromFlow.equals("Personal Loan", ignoreCase = true) ||
            fromFlow.equals("Personal_Loan", ignoreCase = true)
        ) {
            "PERSONAL_LOAN"
        } else if (fromFlow.equals(
                "Purchase Finance",
                ignoreCase = true
            ) || (fromFlow.equals("Loan", ignoreCase = true))
        ) {
            "PURCHASE_FINANCE"
        } else {
            "INVOICE_BASED_LOAN"
        }
        return loanType
    }

    fun handleGeneralException(
        error: Throwable,
        _showInternetScreen: MutableLiveData<Boolean>,
        _showTimeOutScreen: MutableLiveData<Boolean>,
        _unexpectedError: MutableLiveData<Boolean>
    ) {
        when (error) {
            is IOException -> {
                _showInternetScreen.value = true
            }

            is TimeoutException, is TimeoutCancellationException -> {
                _showTimeOutScreen.value = true
            }

            else -> {
                _unexpectedError.value = true
            }
        }
    }

    suspend fun handleResponseException(
        error: ResponseException,
        _showServerIssueScreen: MutableLiveData<Boolean>,
        _middleLoan: MutableLiveData<Boolean>,
        _unAuthorizedUser: MutableLiveData<Boolean>,
        _unexpectedError: MutableLiveData<Boolean>,
        updateErrorMessage: (String) -> Unit,
        context: Context,
        _showLoader: MutableLiveData<Boolean>,
        isFormSearch: Boolean = false,
        searchError: () -> Unit = { }
    ) {
        val statusCode = error.response.status.value
        val responseBody = error.response.readText()
        when (statusCode) {
            400 -> {
                val errorMessage = CommonMethods().parseErrorMessage(responseBody)
                CommonMethods().toastMessage(context, errorMessage)
            }

            401 -> {
                _unAuthorizedUser.value = true
            }

            500 -> {
                _showServerIssueScreen.value = true
            }
            502 -> {
                _showServerIssueScreen.value = true
            }

            417 -> {
                if (isFormSearch) {
                    if (responseBody.contains("already user in the middle of loan application")) {
                        _middleLoan.value = true
                    } else {
                        searchError()
                    }
                } else {
                    try {
                        val jsonObject = JSONObject(responseBody)
                        val data = jsonObject.optString("data", "No data available")
                        updateErrorMessage(data)
                    } catch (e: JSONException) {
                        Log.e("Error", "Error parsing response body", e)
                    }
                    _middleLoan.value = true
                }
            }

            404 -> {
                try {
                    val jsonObject = JSONObject(responseBody)
                    val data = jsonObject.optString("data", "No data available")
                    updateErrorMessage(data)
                } catch (e: JSONException) {
                    Log.e("Error", "Error parsing response body", e)
                }
                _showLoader.value = true
            }
            else -> {
                _unexpectedError.value = true
            }
        }
    }

    fun editingDate(date: String): String {
        val dateString = date
        val actualDate = dateString.split("T")[0]
        return actualDate
    }

    fun isValidDob(dob: String): Boolean {
        val pattern = """^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$"""
//        val pattern = """^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\d{4}$"""
        if (!dob.matches(Regex(pattern))) return false

        return try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val dobDate = LocalDate.parse(dob, formatter)
            val today = LocalDate.now()
            val adultDate = today.minusYears(18)

            dobDate.isBefore(adultDate)
        } catch (e: DateTimeParseException) {
            false
        }
    }

    fun formatWithCommas(number: Int): String {
        try {
            val decimalFormat = DecimalFormat("##,##,###", DecimalFormatSymbols(Locale("en", "IN")))
            return decimalFormat.format(number)
        } catch (e: Exception) {
            print(e.message)
            return ""
        }
    }
}
