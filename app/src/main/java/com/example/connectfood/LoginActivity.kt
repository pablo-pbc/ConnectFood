package com.example.connectfood

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // User's login and password examples
        val validLogin = "01123456000110"
        val validPassword = "admin123"

        //Identifying login and password fields
        val loginInput = findViewById<EditText>(R.id.signInLoginInput)
        val passwordInput = findViewById<EditText>(R.id.signInPasswordInput)

        // Function to validate the user's login, here we have to use some auth method
        fun validateLogin(login: String, password: String): Boolean {
            return login == validLogin && password == validPassword
        }

        // Function to realize the validation and show the error message if needed
        fun signIn() {
            //Getting the data informed by user
            val inputedlogin = loginInput.text.toString()
            val password = passwordInput.text.toString()

            // Formatting the user login 01.123.456/0001-10 -> 01123456000110
            val formatedLogin = inputedlogin.replace("\\D".toRegex(), "")

            if (validateLogin(formatedLogin, password)) {
                // In case of correct login and password
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Going to the next screen
                val helloScreen = Intent(this, DonorReciversHelloScrActivity::class.java)
                helloScreen.putExtra("login", formatedLogin)
                startActivity(helloScreen)
            } else {
                // In case of incorrect login and password
                Toast.makeText(this, "Login ou senha incorreto!", Toast.LENGTH_SHORT).show()
            }
        }

        // Calling the function signIn by clicking on the button signInBtn
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        signInBtn.setOnClickListener { signIn() }

        // Navigating to the Register screen by clicking on register button
        val signUpBtn = findViewById<TextView>(R.id.signInSignUpBtn)
        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}