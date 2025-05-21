package com.github.sugunasriram.fisloanlibv4


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

class ThirdScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdScreenContent()
        }
    }
}

@Composable
fun ThirdScreenContent() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Image 1",
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally)
                .weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.image2),
            contentDescription = "Image 2",
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally)
                .weight(1f)
        )
        Button(
            onClick = {
                context.startActivity(Intent(context, SecondScreenActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.body1,
            )
        }
    }
}