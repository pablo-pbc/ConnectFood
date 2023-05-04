package com.example.connectfood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3000 // 3 seconds

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        Handler().postDelayed({
            val intent = Intent(this, DonorRecipientsHelloScrActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY.toLong())
    }

}