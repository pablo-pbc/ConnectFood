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
class DonorRecipientsFilterAllActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_filter_all_screen)

        val myImageView: ImageView = findViewById(R.id.filterAllNoLoggedUserGridLogo)
        val imageListView: ImageView = findViewById(R.id.filterAllNoLoggedUserListLogo)
        val imageUrl = "https://img.freepik.com/vetores-premium/modelo-de-design-de-logotipo-de-restaurante_79169-56.jpg?w=2000"

        Glide.with(this)
            .load(imageUrl)
            .transform(RoundedCorners(25))
            .into(myImageView)

        Glide.with(this)
            .load(imageUrl)
            .transform(RoundedCorners(25))
            .into(imageListView)
    }
}
