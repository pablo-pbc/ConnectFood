package com.example.connectfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Variáveis de exemplo de login e senha válidas
        val validLogin = "01123456000110"
        val validPassword = "password123"

        //Identificando os campos de login e senha
        val loginInput = findViewById<EditText>(R.id.signInLoginInput)
        val passwordInput = findViewById<EditText>(R.id.signInPasswordInput)

        //Pegando os valores informados pelo usuário
        val inputedlogin = loginInput.text.toString()
        val password = passwordInput.text.toString()

        // Formatando o login do usuario 01.123.456/0001-10 -> 01123456000110
        val formatedLogin = inputedlogin.replace("[^0-9]".toRegex(), "")

        // Função para validação do login do usuário, aqui tem que usar algum método de autentificação
        fun validateLogin(login: String, password: String): Boolean {
            return login == validLogin && password == validPassword
        }

        // Função para realizar a validação e exibir mensagem de erro caso necessário
        fun signIn() {
            if (validateLogin(formatedLogin, password)) {
                // Caso as informações de login sejam válidas, realizar a ação desejada
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Indo para a próxima tela
                val helloScreen = Intent(this, DonorRecipientsHelloScrActivity::class.java)
                intent.putExtra("login", formatedLogin)
                startActivity(helloScreen)
            } else {
                // Caso as informações de login sejam inválidas, exibir mensagem de erro
                Toast.makeText(this, "Login ou senha incorreto!", Toast.LENGTH_SHORT).show()
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