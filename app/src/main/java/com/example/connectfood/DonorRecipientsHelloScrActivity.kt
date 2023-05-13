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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.w3c.dom.Text
import java.util.*

@Suppress("DEPRECATION")
class DonorRecipientsHelloScrActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_hello_screen)

        /*
        * Trazer as informações do usario para colocar o nome dele, foto e slogan
        * Lembrar de ativar o GLIDE
        * */
        // Setting the variables for handler timing function
        val SPLASH_DELAY = 5000 // 3 seconds
        val TEXT_DELAY = 2500 // 3 seconds

        //Getting the dynamic fields
        val userLocationTextView = findViewById<TextView>(R.id.helloScreenLoggedUserLocation)
        val userLogoImageView = findViewById<ImageView>(R.id.helloScreenLoggedUserLogo)
        val userNameTextView = findViewById<TextView>(R.id.helloScreenLoggedUserName)
        val userSloganTextView = findViewById<TextView>(R.id.helloScreenLoggedUserslogan)
        val intentionMessageTitle = findViewById<TextView>(R.id.helloScreenStandarTileMessage)
        val intentionMessageSub = findViewById<TextView>(R.id.helloScreenStandarSubMessage)

        //Variable for fade in and out animation
        val animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        //Variables to call the handler function
        val timerOne = Handler()
        val timerTwo = Handler()

        //Retrieving the user's login from signIn activity
        val Userlogin = intent.getStringExtra("login").toString()

        //Function to get the user's name, photo and slogan from the ddb by using your UserLogin
        fun gettingUserName(Userlogin: String) {
            //Dynamic variables following user login information
            var userName = Userlogin
            var userPhoto = "https://img.freepik.com/vetores-premium/modelo-de-design-de-logotipo-de-restaurante_79169-56.jpg?w=2000"
            var userSlogan = "Quer hamburguer? Então vem com a gente!"
            var userStreet = "Av. Lins de Vasconcelos"
            var userAddressNumber = "1222"
            var userLocation = "$userStreet - $userAddressNumber"

            //Setting dynamically the user's information on salutation screen
            userLocationTextView.text = userLocation
            userNameTextView.text = userName
            userSloganTextView.text = userSlogan
            //Glide to set the img
            Glide.with(this)
                .load(userPhoto)
                .into(userLogoImageView)

            //Timer Function to set a new text after a while
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
            }, TEXT_DELAY.toLong())

            //Timer Function to navigate to the next screen after a while
            timerTwo.postDelayed({
                val intent = Intent(this, DonorRecipientsFilterAllActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_DELAY.toLong())
        }
        gettingUserName(Userlogin);
    }
}
