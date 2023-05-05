package com.example.connectfood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*

@Suppress("DEPRECATION")
class DonorRecipientsListActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_list_screen)

        val myImageView: ImageView = findViewById(R.id.my_image_view)

        Glide.with(this)
            .load("https://www.palpitedigital.com/y/5327/imagens-google-e1604596848141.jpg")
            .centerCrop()
            .transform(RoundedCorners(16))
            .into(myImageView)
    }
}
