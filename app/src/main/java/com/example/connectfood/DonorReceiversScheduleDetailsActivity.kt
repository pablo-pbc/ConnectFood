package com.example.connectfood

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.button.MaterialButton
import java.util.*

@Suppress("DEPRECATION")
class DonorReceiversScheduleDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_schedule_details_screen)

        //Getting elements from current screen
        val likeIcon = findViewById<ImageView>(R.id.likeOrUnlikedIcon)
        val backButton = findViewById<LinearLayout>(R.id.scheduleDetailsBackBtn)
        val banner = findViewById<ImageView>(R.id.scheduleDetailsNoLoggedUserBanner)
        val userNoLoggedDataLayout = findViewById<LinearLayout>(R.id.scheduleDataLayout)
        val userNoLoggedName = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserName)
        val confirmButton = findViewById<MaterialButton>(R.id.scheduleDetailsConfirmScheduleBtn)
        val userNoLoggedAddress = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserAdress)
        val userNoLoggedPhone = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserTelephone)
        val userNoLoggedWhatsapp = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserWhatsapp)

        //Getting val from intent
        val intentImageUrl = intent.getStringExtra("urlImage")

        // Use Glide to load the image
        Glide.with(this)
            .load(intentImageUrl)
            .transform(RoundedCorners(25))
            .into(banner)

        backButton.setOnClickListener(){
            val filterAll = Intent(this, DonorReceiversFilterAllActivity::class.java)
            startActivity(filterAll)
        }

        confirmButton.setOnClickListener() {
            confirmButton.text = "Finalizar agendamento"
            userNoLoggedDataLayout.visibility = View.VISIBLE
        }

    }
}
