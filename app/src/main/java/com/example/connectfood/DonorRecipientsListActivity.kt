package com.example.connectfood

import android.R
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import android.content.Intent
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import java.util.*


@Suppress("DEPRECATION")
class DonorRecipientsListActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3000 // 3 seconds

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_list)

        val myImageView: ImageView = findViewById(R.id.my_image_view)

        Glide.with(this)
            .load("https://www.palpitedigital.com/y/5327/imagens-google-e1604596848141.jpg")
            .centerCrop()
            .transform(RoundedCorners(16))
            .into(myImageView)


//        Handler().postDelayed({
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, SPLASH_DELAY.toLong()
    }
}