package com.example.connectfood

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class DonorRecipientsListActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3000 // 3 seconds

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_list)

//        Handler().postDelayed({
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, SPLASH_DELAY.toLong()
    }

}