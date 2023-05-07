package com.example.connectfood


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Identificando os EditText da tela de cadastro
        val inputCNPJ = findViewById<EditText>(R.id.signUpInputCNPJ)
        val inputEmail = findViewById<EditText>(R.id.signUpInputEmail)
        val inputTelefone = findViewById<EditText>(R.id.signUpInputTelephone)
        val inputCEP = findViewById<EditText>(R.id.signUpInputCEP)
        val inputBairro = findViewById<EditText>(R.id.signUpInputBairro)
        val inputEndereco = findViewById<EditText>(R.id.signUpInputEndereco)
        val inputCidade = findViewById<EditText>(R.id.signUpInputCidade)

        //Pegando os valores de todos os EditTex
        val stringCNPJ = inputCNPJ.text.toString()
        val stringEmail = inputEmail.text.toString()
        val stringTelefone = inputTelefone.text.toString()
        val stringCEP = inputCEP.text.toString()
        val stringBairro = inputBairro.text.toString()
        val stringEndereco = inputEndereco.text.toString()
        val stringCidade = inputCidade.text.toString()

        //Formando o CNPJ para fazer o request : 01.123.456/0001-10 -> 01123456000110
        val formatedCNPJ = findViewById<EditText>(R.id.signUpInputCNPJ).text.toString().replace("[^0-9]".toRegex(), "")

        fun requestCNPJ (){
            val url = "https://www.receitaws.com.br/v1/cnpj/$formatedCNPJ"
            Log.d(stringCNPJ, url);
        }

        inputEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requestCNPJ ()
            }
        }
    }
}