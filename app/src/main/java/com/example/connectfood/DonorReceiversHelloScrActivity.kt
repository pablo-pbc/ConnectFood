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
import java.util.*

class DonorReceiversHelloScrActivity : AppCompatActivity() {

    private lateinit var userLocationTextView: TextView
    private lateinit var userLogoImageView: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var userSloganTextView: TextView
    private lateinit var intentionMessageTitle: TextView
    private lateinit var intentionMessageSub: TextView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_hello_screen)

        // Setting the variables for handler timing function
        val splashDelay = 5000 // 3 seconds
        val textDelay = 2500 // 3 seconds

        // Initialize the view variables using lateinit
        userLocationTextView = findViewById(R.id.helloScreenLoggedUserLocation)
        userLogoImageView = findViewById(R.id.helloScreenLoggedUserLogo)
        userNameTextView = findViewById(R.id.helloScreenLoggedUserName)
        userSloganTextView = findViewById(R.id.helloScreenLoggedUserslogan)
        intentionMessageTitle = findViewById(R.id.helloScreenStandarTileMessage)
        intentionMessageSub = findViewById(R.id.helloScreenStandarSubMessage)

        // Variable for fade in and out animation
        val animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Variables to call the handler function
        val timerOne = Handler()
        val timerTwo = Handler()

        // Retrieving the user's login from signIn activity
        val cnpj = intent.getStringExtra("cnpj")
        val endereco = intent.getStringExtra("endereco")
        val nome = intent.getStringExtra("nome")
        val photo = intent.getStringExtra("photo")
        val description = intent.getStringExtra("description")

        // Function to get the user's name, photo and slogan from the ddb by using your UserLogin
        fun gettingUserName() {
            // Dynamic variables following user login information
            val userName = nome
            val userPhoto = photo
            val userSlogan = description
            val userLocation = endereco

            // Setting dynamically the user's information on salutation screen
            userLocationTextView.text = userLocation
            userNameTextView.text = userName
            userSloganTextView.text = userSlogan
            // Glide to set the image
            Glide.with(this)
                .load(userPhoto)
                .into(userLogoImageView)

            // Timer Function to set a new text after a while
            timerOne.postDelayed({
                intentionMessageTitle.startAnimation(animOut)
                timerOne.postDelayed({
                    intentionMessageTitle.text = "Estamos quase lá"
                    intentionMessageTitle.startAnimation(animIn)
                }, animOut.duration)

                intentionMessageSub.startAnimation(animOut)
                timerOne.postDelayed({
                    intentionMessageSub.text = "Estamos listando os locais onde recebem doações"
                    intentionMessageSub.startAnimation(animIn)
                }, animOut.duration)
            }, textDelay.toLong())

            // Timer Function to navigate to the next screen after a while
            timerTwo.postDelayed({
                val intent = Intent(this, DonorReceiversFilterAllActivity::class.java)
                intent.putExtra("endereco", userLocation)
                intent.putExtra("photo", userPhoto)
                intent.putExtra("nome", userName)
                intent.putExtra("cnpj", cnpj)
                startActivity(intent)
                finish()
            }, splashDelay.toLong())
        }

        gettingUserName()
    }
}
