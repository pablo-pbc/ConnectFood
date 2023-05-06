package com.example.connectfood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.TextView
import java.util.*

@Suppress("DEPRECATION")
class DonorRecipientsHelloScrActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_hello_screen)

        val SPLASH_DELAY = 5000 // 3 seconds
        val TEXT_DELAY = 2500 // 3 seconds
        val intentionMessageTitle: TextView = findViewById(R.id.helloScreenStandarTileMessage)
        val intentionMessageSub: TextView = findViewById(R.id.helloScreenStandarSubMessage)

        val animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val timerOne = Handler()
        val timerTwo = Handler()

        timerOne.postDelayed({
            intentionMessageTitle.startAnimation(animOut)
            timerOne.postDelayed({
                intentionMessageTitle.text = "Estamos quase lá"
                intentionMessageTitle.startAnimation(animIn)
            }, animOut.duration)
        }, TEXT_DELAY.toLong())

        timerOne.postDelayed({
            intentionMessageSub.startAnimation(animOut)
            timerOne.postDelayed({
                intentionMessageSub.text = "Estamos listando os locais onde recebem doações"
                intentionMessageSub.startAnimation(animIn)
            }, animOut.duration)
        }, TEXT_DELAY.toLong())

        timerTwo.postDelayed({
            val intent = Intent(this, DonorRecipientsFilterAllActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
