package com.example.connectfood

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*

@Suppress("DEPRECATION")
class DonorReciversScheduleDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_schedule_details_screen)

        val filterAllNoLoggedUserGridLogo = findViewById<ImageView>(R.id.filterAllNoLoggedUserLogo)
        val imageListView = findViewById<ImageView>(R.id.filterAllNoLoggedUserLogo)
        val imageUrl = "https://www.palpitedigital.com/y/5327/imagens-google-e1604596848141.jpg"

        Glide.with(this)
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(25))
            .into(filterAllNoLoggedUserGridLogo)

        Glide.with(this)
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(25))
            .into(imageListView)
    }
}