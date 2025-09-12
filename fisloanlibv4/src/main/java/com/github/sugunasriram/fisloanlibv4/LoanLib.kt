package com.github.sugunasriram.fisloanlibv4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.fiscode.app.MainActivity
import com.github.sugunasriram.fisloanlibv4.fiscode.dataStore
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import java.io.Serializable

object LoanLib {
    var callback: ((LoanDetails) -> Unit)? = null

    fun launchFirstScreen(context: Context) {
        Toast.makeText(context, "Launching First Screen", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, FirstScreenActivity::class.java)
        context.startActivity(intent)
    }

    @Composable
    fun ImagePreview(
        image: Painter = painterResource(id = R.drawable.image1),
        modifier: Modifier = Modifier,
        description: String = "Testing",
        contentDescription: String = "",
        onImageClick: () -> Unit = {}
    ) {
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .shadow(15.dp, RoundedCornerShape(15.dp))
                .clickable { onImageClick() }
        ) {
            Image(
                painter = image,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }

    fun DisplayImageAndTwoButtons(context: Context) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(android.graphics.Color.WHITE)
        }

        val imageView = ImageView(context).apply {
            setImageResource(R.drawable.google_image)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }

        val buttonLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val backButton = android.widget.Button(context).apply {
            text = "Back"
            setOnClickListener {
                // Handle Back action
            }
        }

        val forwardButton = android.widget.Button(context).apply {
            text = "Forward"
            setOnClickListener {
                // Handle Forward action
            }
        }

        buttonLayout.addView(backButton)
        buttonLayout.addView(forwardButton)

        layout.addView(imageView)
        layout.addView(buttonLayout)

        val activity = context as Activity
        activity.setContentView(layout)
    }

    fun LaunchThirdScreen(context: Context) {
        Toast.makeText(context, "Launching Third Screen", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, ThirdScreenActivity::class.java)
        context.startActivity(intent)
    }
    fun LaunchFISApp(context: Context) {
        Toast.makeText(context, "Launching FIS", Toast.LENGTH_SHORT).show()

        // Initialize the library
        init(context)

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    private fun init(context: Context) {
        if (!TokenManager.isInitialized()) {
            TokenManager.initialize(context.dataStore)
        }
    }

    data class PersonalDetails(
        val name: String = "",
        val dob: String = "",
        val personalEmailId: String = "",
        val officialEmailId: String = "",
        val gender: String = "",
        val address1: String = "",
        val pincode1: String = "",
        val address2: String = "",
        val pincode2: String = ""
    ) : Serializable

    data class ProductDetails(
        val productCategory: String = "",
        val productSKUID: String = "",
        val productBrand: String = "",
        val productPrice: Double = 0.0,
        val downpayment: Double = 0.0,
        val merchantPan: String = "",
        val merchantGst: String = "",
        val merchantBankAccountNumber: String = "",
        val merchantIfscCode: String = "",
        val merchantBankAccountHolderName: String = ""
    ) : Serializable

    data class SessionDetails(
        val sessionId: String = "",
        val loanId: String = ""
    ) : Serializable

    fun LaunchFISAppWithParams(
        context: Context,
//        personalDetails: PersonalDetails,
//        productDetails: ProductDetails
    ) {
//        Toast.makeText(context, "Launching FIS with Params", Toast.LENGTH_SHORT).show()

        // Initialize the library
        init(context)

        val intent = Intent(context, MainActivity::class.java)
//        intent.putExtra("personalDetails", personalDetails)
//        intent.putExtra("productDetails", productDetails)

        context.startActivity(intent)
    }

    data class LoanDetails(
        val sessionId: String,
        val loanAmount: Double,
        val interestRate: Double,
        val tenure: Int,
        val downpaymentAmount: Int
    ) : Serializable
//    fun LaunchFISAppWithParamsAndCallback(
//        context: Context,
//        personalDetails: PersonalDetails,
//        productDetails: ProductDetails,
//        sessionDetails: SessionDetails ? = null,
//        callback: (LoanDetails) -> Unit
//    ) {
//        Toast.makeText(context, "Launching FIS with Params", Toast.LENGTH_SHORT).show()
//
//        // Initialize the library
//        init(context)
//
//        val intent = Intent(context, MainActivity::class.java)
//        intent.putExtra("personalDetails", personalDetails)
//        intent.putExtra("productDetails", productDetails)
//        intent.putExtra("sessionDetails", sessionDetails)
//
//        LoanLib.callback = callback
//
//        // (context as Activity).startActivityForResult(intent, 2001)
//        context.startActivity(intent)
//    }

    fun LaunchFISAppWithParamsAndCallback(
        context: Context,
        sessionDetails: SessionDetails ? = null,
        callback: (LoanDetails) -> Unit
    ) {
//        Toast.makeText(context, "Launching FIS with Params", Toast.LENGTH_SHORT).show()

        // Initialize the library
        init(context)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("sessionDetails", sessionDetails)

        LoanLib.callback = callback

        // (context as Activity).startActivityForResult(intent, 2001)
        context.startActivity(intent)
    }

    fun LaunchFISAppForLoanDetails(
        context: Context,
        sessionDetails: SessionDetails ? = null,
        callback: (LoanDetails) -> Unit
    ) {
//        Toast.makeText(context, "Launching FIS with Params", Toast.LENGTH_SHORT).show()

        // Initialize the library
        init(context)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("sessionDetails", sessionDetails)

        LoanLib.callback = callback

        // (context as Activity).startActivityForResult(intent, 2001)
        context.startActivity(intent)
    }
}
