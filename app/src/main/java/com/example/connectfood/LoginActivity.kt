package com.example.connectfood

import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Variáveis de exemplo de login e senha válidas
        val validLogin = "user123"
        val validPassword = "password123"

        // Simulação de busca das informações de login no banco de dados
        fun validateLogin(login: String, password: String): Boolean {
            // Aqui seria o código para buscar as informações no banco de dados
            // Neste exemplo, apenas compararemos com as variáveis de exemplo
            return login == validLogin && password == validPassword
        }

        // Função para realizar a validação e exibir mensagem de erro caso necessário
        fun signIn() {
            val loginInput = findViewById<EditText>(R.id.signInLoginInput)
            val passwordInput = findViewById<EditText>(R.id.signInPasswordInput)

            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            if (validateLogin(login, password)) {
                // Caso as informações de login sejam válidas, realizar a ação desejada
                Toast.makeText(this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()

                val helloScreen = Intent(this, DonorRecipientsHelloScrActivity::class.java)
                startActivity(helloScreen)

            } else {
                // Caso as informações de login sejam inválidas, exibir mensagem de erro
                Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuração do botão de login para chamar a função signIn()
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        signInBtn.setOnClickListener { signIn() }

        // Configuração do botão de cadastro para ir para a tela de cadastro
        val signUpBtn = findViewById<TextView>(R.id.signInSignUpBtn)
        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}