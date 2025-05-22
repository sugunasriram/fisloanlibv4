package com.github.sugunasriram.fisloanlibv4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FirstScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            startActivity(Intent(this, SecondScreenActivity::class.java))
        }
    }
}
