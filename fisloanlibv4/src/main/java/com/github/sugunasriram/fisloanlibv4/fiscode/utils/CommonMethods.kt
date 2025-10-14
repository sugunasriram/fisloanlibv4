package com.github.sugunasriram.fisloanlibv4.fiscode.utils

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.OrderPaymentStatusItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NegativeCommonScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.RequestTimeOutScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.UnAuthorizedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.UnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.convertUTCToLocalDateTime
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.coolOffPeriodDate
import io.ktor.client.features.HttpRequestTimeoutException
import io.ktor.client.features.ResponseException
import io.ktor.client.statement.readText
import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Date
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
    private val panNumberPattern = "^[A-Z]{5}[0-9]{4}[A-Z]$"
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
            "^(?!.*\\.\\.)(?!.*\\.@)(?!.*@\\.)(?!.*\\s)([a-zA-Z0-9]+[\\w.-]*)@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,16}$"
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

    fun getThreeMonthRange(fromDate: LocalDate = LocalDate.now()): String {
        val startDate = fromDate.minusMonths(3)
        val endDate = fromDate

        fun getDaySuffix(day: Int): String {
            return when {
                day in 11..13 -> "th"
                day % 10 == 1 -> "st"
                day % 10 == 2 -> "nd"
                day % 10 == 3 -> "rd"
                else -> "th"
            }
        }

        fun formatWithSuffix(date: LocalDate): String {
            val day = date.dayOfMonth
            val suffix = getDaySuffix(day)
            val formatter = DateTimeFormatter.ofPattern("d'$suffix' MMMM yyyy", Locale.ENGLISH)
            return date.format(formatter)
        }

        return "${formatWithSuffix(startDate)} - ${formatWithSuffix(endDate)}"
    }

    private fun parseErrorMessage(responseBody: String): String {
        val jsonObject = JSONObject(responseBody)
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
        val formatter = DecimalFormat("#,##,###", DecimalFormatSymbols(Locale("en", "IN")))
        return "₹" + formatter.format(amount)
    }

    fun parseAndFormatIndianCurrency(input: String): String {
        val cleaned = input
            .replace("INR", "", ignoreCase = true) // Remove INR
            .replace("[^\\d.]".toRegex(), "")      // Remove anything except digits and dot
            .trim()

        val amount = cleaned.toDoubleOrNull() ?: 0.0
        return formatIndianDoubleCurrency(amount)
    }
    fun formatIndianDoubleCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        format.isGroupingUsed = true
        return format.format(amount)
    }

    fun roundToNearestHundred(value: Float): Float {
        return Math.round(value / 100) * 100.toFloat()
    }
    private fun formatDateFromTimestamp(timestamp: String): String {
        return try {
            val zonedDateTime = ZonedDateTime.parse(timestamp)
            val localDateTime = zonedDateTime
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            localDateTime.format(formatter)
        } catch (e: Exception) {
            "" // Return empty string on failure
        }
    }
    @Composable
    fun displayFormattedDate(timeStamp: String): String {
        val formattedDate = remember {
            val zonedDateTime = ZonedDateTime.parse(timeStamp)
            val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
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
            onClick = {
//                navigateApplyByCategoryScreen(navController)
                navigateToFISExitScreen(navController, loanId="4321")
            }
        )
    }

    @Composable
    fun ShowTimeOutErrorScreen(navController: NavHostController) {
        RequestTimeOutScreen(navController, onClick = {
        //    navigateApplyByCategoryScreen(navController)
            navigateToFISExitScreen(navController, loanId="4321")
        })
    }

    @Composable
    fun ShowServerIssueErrorScreen(navController: NavHostController) {
        NegativeCommonScreen(
            navController = navController,
            errorText = stringResource(id = R.string.server_temporarily_unavailable),
            solutionText = stringResource(id = R.string.please_try_again_after_sometime),
//            onClick = { navigateApplyByCategoryScreen(navController) }
            onClick = { navigateToFISExitScreen(navController, loanId="4321") }
        )
    }

    @Composable
    fun ShowUnexpectedErrorScreen(navController: NavHostController) {
        UnexpectedErrorScreen(navController = navController,
//            onClick = { navigateApplyByCategoryScreen(navController) }
            onClick = { navigateToFISExitScreen(navController, loanId="4321") }
        )
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
//            onClick = { navigateApplyByCategoryScreen(navController) }
            onClick = { navigateToFISExitScreen(navController, loanId="4321") }
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
//            onClick = { navigateApplyByCategoryScreen(navController) }
            onClick = { navigateToFISExitScreen(navController, loanId="4321") }
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
    fun generatePdfAndNotify(
        context: Context,
        loanDetails: OfferResponseItem,
        payment: List<OrderPaymentStatusItem>,
        title: String
    ) {
        val lenderName = loanDetails.providerDescriptor?.name.orEmpty()
        val fileName =
            "$title-${SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date())}.pdf"
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsDir, fileName)
        try {
            val document = PdfDocument()
            val paint = Paint().apply { textSize = 14f }
            val pageWidth = 595
            val pageHeight = 842
            var pageNumber = 1

            var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            var page = document.startPage(pageInfo)
            var canvas = page.canvas
            var y = 80

            fun newPage() {
                document.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = document.startPage(pageInfo)
                canvas = page.canvas
                y = 50
            }

            fun drawTextLine(text: String) {
                if (y > 800) { // near end of page
                    newPage()
                }
                canvas.drawText(text, 30f, y.toFloat(), paint)
                y += 25
            }

            fun breakTextRightAligned(text: String, maxWidth: Float, paint: Paint): List<String> {
                val lines = mutableListOf<String>()

                if (paint.measureText(text) <= maxWidth) {
                    lines.add(text)
                    return lines
                }

                var start = 0
                var end = text.length

                while (start < text.length) {
                    var lineEnd = end
                    while (lineEnd > start) {
                        val candidate = text.substring(start, lineEnd)
                        if (paint.measureText(candidate) <= maxWidth) {
                            lines.add(candidate)
                            start = lineEnd
                            break
                        }
                        lineEnd--
                    }

                    // If we didn't break (very long word), break forcibly
                    if (lineEnd == start) {
                        val maxChars =
                            maxWidth / paint.measureText("W") // estimate based on wide char
                        val forcedEnd = (start + maxChars).toInt().coerceAtMost(text.length)
                        lines.add(text.substring(start, forcedEnd))
                        start = forcedEnd
                    }
                }

                return lines
            }

            fun drawLabelValueLine(label: String, value: String) {
                if (y > 800) newPage()

                val labelX = 30f
                val pageWidth = pageInfo.pageWidth
                val maxWidth = pageWidth - 2 * labelX
                val valueMaxWidth = maxWidth - 200f // leave space for label

                val isLink = value.startsWith("http")
                val linkPaint = Paint(paint).apply {
                    color = Color.BLUE
                    isUnderlineText = true
                    textSize = paint.textSize
                }

                val valuePaint = if (isLink) linkPaint else paint
                val valueLines = breakTextRightAligned(value, valueMaxWidth, valuePaint)
                canvas.drawText(label, labelX, y.toFloat(), paint)

                valueLines.forEachIndexed { index, line ->
                    if (y > 800) newPage()
                    val textWidth = valuePaint.measureText(line)
                    val valueX = pageWidth - textWidth - 30f // 30f right margin
                    if (index == 0) {
                        // First line already drawn above, just skip
                    } else {
                        y += 25
                    }
                    canvas.drawText(line, valueX, y.toFloat(), valuePaint)
                }

                y += 25 // extra space after label-value block
            }

            fun drawSectionHeader(title: String) {
                if (y > 800) newPage()
                paint.textSize = 18f
                paint.isFakeBoldText = true
                paint.isUnderlineText = true
                canvas.drawText(title, 30f, y.toFloat(), paint)
                y += 30
                paint.isFakeBoldText = false
                paint.isUnderlineText = false
                paint.textSize = 14f
            }

            fun drawEmiTable() {
                var emiCounter = 0
                val totalWidth = pageInfo.pageWidth - 80f
                val columnWidths = listOf(
                    0.25f * totalWidth, // EMI No
                    0.35f * totalWidth, // Date
                    0.25f * totalWidth, // Amount
                    0.25f * totalWidth  // Status
                )
                val headers = listOf("EMI No.", "EMI Due Date", "EMI Amount", "Status")

                // Header row
                var x = 30f
                paint.isFakeBoldText = true
                headers.forEachIndexed { index, header ->
                    canvas.drawText(header, x, y.toFloat(), paint)
                    x += columnWidths[index]
                }
                paint.isFakeBoldText = false
                y += 25

                loanDetails.payments?.forEach { payment ->
                    if (payment?.type == "POST_FULFILLMENT") {
//                        val emiNum = payment.id.orEmpty()
                        val emiNum = (++emiCounter).toString()
                        val dueDate = payment.time?.range?.start?.let {
                            formatDateFromTimestamp(it)
                        }.orEmpty()
                        val amount ="₹${payment.params?.amount.orEmpty()}"
                        val status = payment.status.orEmpty()

                        val rowData = listOf(emiNum, dueDate, amount, status)

                        x = 30f
                        rowData.forEachIndexed { index, cell ->
                            canvas.drawText(cell, x, y.toFloat(), paint)
                            x += columnWidths[index]
                        }

                        y += 20
                        if (y > 800) newPage()
                    }
                }

                y += 10 // spacing after the table
            }

            fun drawPaymentHistoryTable() {
                if (payment.isEmpty()) return
                drawTextLine("")
                drawSectionHeader("Payment History")
                val totalWidth = pageInfo.pageWidth - 80f
                val columnWidths = listOf(
                    0.45f * totalWidth, // Payment Type (label)
                    0.35f * totalWidth,  // Amount
                    0.35f * totalWidth   // Status
                )

                val headers = listOf("Payment Type", "Amount", "Status")

                var x = 30f
                paint.isFakeBoldText = true
                headers.forEachIndexed { index, header ->
                    canvas.drawText(header, x, y.toFloat(), paint)
                    x += columnWidths[index]
                }
                paint.isFakeBoldText = false
                y += 25

                payment.forEach { item ->
                    if (y > 800) newPage()
                    val label = CommonMethods().displayFormattedText(item.time?.label.orEmpty())
                    val amount = "₹${item.params?.amount.orEmpty()}"
                    val status = item.status.orEmpty()

                    val rowData = listOf(label, amount, status)

                    x = 30f
                    rowData.forEachIndexed { index, cell ->
                        canvas.drawText(cell, x, y.toFloat(), paint)
                        x += columnWidths[index]
                    }

                    y += 20
                }

                y += 10 // Spacing after table
            }

            drawSectionHeader("Loan Application Details: $lenderName")

            // Fill from loanDetails
            drawLabelValueLine(
                "Applicant Name",
                loanDetails.fulfillments?.firstOrNull()?.customer?.person?.name.orEmpty()
            )
            drawLabelValueLine(
                "Mobile Number",
                loanDetails.fulfillments?.firstOrNull()?.customer?.contact?.phone.orEmpty()
            )
            drawLabelValueLine(
                "Email",
                loanDetails.fulfillments?.firstOrNull()?.customer?.contact?.email.orEmpty().lowercase()
            )
//            drawLabelValueLine("Application ID", loanDetails.itemId ?: "")
            drawLabelValueLine("Application ID", loanDetails.id ?: "")
            drawLabelValueLine("Loan Type", loanDetails.itemDescriptor?.name.orEmpty())
            drawLabelValueLine("Loan Amount", formatIndianDoubleCurrency(loanDetails.itemPrice?.value.orEmpty().toDouble()))
            loanDetails.itemTags?.forEach { itemTags ->
                if (itemTags?.display == true) {
                    itemTags.tags.forEach { tag ->
                        val label = CommonMethods().displayFormattedText(tag.key)
                        val value = if (tag.key.contains("cool_off", ignoreCase = true)) {
                            CommonMethods().uTCToLocalDateTimeConversion(tag.value)
                        }else if (tag.key.contains("amount", ignoreCase = true)) {
                        CommonMethods().formatIndianDoubleCurrency(tag.value.toDouble())
                    }  else {
                            tag.value
                        }
                        drawLabelValueLine(label, value)
                    }
                }
            }
            drawTextLine("")
            drawSectionHeader("LOAN SUMMARY:")
            loanDetails.quoteBreakUp?.forEach {
                val label = CommonMethods().displayFormattedText(it?.title.orEmpty())
                val value = formatIndianDoubleCurrency(it?.value.orEmpty().toDouble())
                drawLabelValueLine(label, value)
            }
            drawTextLine("")
            drawSectionHeader("EMI Details:")
            drawEmiTable()
            drawPaymentHistoryTable()
            drawTextLine("")
            drawSectionHeader("Lender Info:")
            loanDetails.providerTags
                ?.firstOrNull { it?.name == "Lsp Info" }
                ?.tags
                ?.forEach { (key, value) ->
                    val label = CommonMethods().displayFormattedText(key)
                    drawLabelValueLine(label, value)
                }
            drawTextLine("")
            drawSectionHeader("Grievance Redressal Officer(GRO) Details:")
            loanDetails.providerTags
                ?.firstOrNull { it?.name == "Contact Info" }
                ?.tags
                ?.forEach { (key, value) ->
                    val label = CommonMethods().displayFormattedText(key)
                    drawLabelValueLine(label, value)
                }
            document.finishPage(page)
            document.writeTo(FileOutputStream(pdfFile))
            document.close()

            // 2. Trigger media scan so file is visible in File Managers
            MediaScannerConnection.scanFile(
                context,
                arrayOf(pdfFile.absolutePath),
                arrayOf("application/pdf"),
                null
            )

            // 3. Show custom notification
            showNotification(context, pdfFile, title)

            CommonMethods().toastMessage(context, "Downloading Loan Details")
        } catch (e: Exception) {
            CommonMethods().toastMessage(context, "Download failed: ${e.localizedMessage}")
            Log.d("res_H", "Error: ${e.localizedMessage}")
        }
    }

    private fun showNotification(context: Context, file: File, title: String) {
        val channelId = "pdf_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, "PDF Downloads", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle("PDF Downloaded")
            .setContentText(file.name)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(1010, notification)
    }

    fun downloadPdf(context: Context, url: String, fileName: String,title:String) {
        try {
            val timestamp = SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault())
                .format(Date())

            val fullTitle = "$title - $timestamp"
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle(fullTitle)
                .setDescription("Downloading $fileName")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title-$timestamp.pdf")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            CommonMethods().toastMessage(
                context = context, toastMsg = "Downloading Loan Agreement"
            )

        } catch (e: Exception) {
            CommonMethods().toastMessage(
                context = context, toastMsg =  "Download failed: ${e.message}"
            ) }
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
                UnexpectedErrorScreen(navController = navController,
//                    onClick = { navigateApplyByCategoryScreen(navController = navController) }
                    onClick = { navigateToFISExitScreen(navController, loanId="4321") }
                )
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

            is TimeoutException, is TimeoutCancellationException, is HttpRequestTimeoutException -> {
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
//                val errorMessage = CommonMethods().parseErrorMessage(responseBody)
//                CommonMethods().toastMessage(context, errorMessage)
                _unexpectedError.value = true
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
            507 -> {
                try {
                    val jsonObject = JSONObject(responseBody)
                    val data = jsonObject.optString("data", "No data available")
                    updateErrorMessage(data)
                } catch (e: JSONException) {
                    Log.e("Error", "Error parsing response body", e)
                }
                _middleLoan.value = true
            }

            417 -> {
                try {
                    val jsonObject = JSONObject(responseBody)
                    val data = jsonObject.optString("data", "No data available")
                    updateErrorMessage(data)
                } catch (e: JSONException) {
                    Log.e("Error", "Error parsing response body", e)
                }
                _middleLoan.value = true
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

    fun parseTenureToMonths(value: String?): String? {
        if (value == null) return null
        val cleaned = value.lowercase(Locale.ROOT).trim()
        return when {
            cleaned.contains("year") -> {
                // get numeric part and multiply by 12
                val number = cleaned.replace("[^0-9]".toRegex(), "").toIntOrNull()
                number?.times(12)?.toString()
            }
            cleaned.contains("month") -> {
                cleaned.replace("[^0-9]".toRegex(), "").takeIf { it.isNotEmpty() }
            }
            else -> cleaned.replace("[^0-9]".toRegex(), "").takeIf { it.isNotEmpty() }
        }
    }
    private fun uTCToLocalDateTimeConversion(utcDateTime: String): String {
        val zonedDateTime =
            ZonedDateTime.parse(utcDateTime).withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
        val formatter = DateTimeFormatter.ofPattern("hh:mm a, dd MMM yyyy", Locale.getDefault())
        val dateTimeStr = zonedDateTime.format(formatter)
        val formatedStr = "Till - $dateTimeStr"
        return formatedStr
    }
}
