package com.example.connectfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //identificando os CHECKBOX
        val doadorCheckBox = findViewById<CheckBox>(R.id.signUpcheckBoxDoador)
        val instituicaoCheckBox = findViewById<CheckBox>(R.id.signUpcheckBoxInstituicao)
        var userType = ""

        //Identificando os EditText da tela de cadastro
        val inputCNPJ = findViewById<EditText>(R.id.signUpInputCNPJ)
        val inputFantasia = findViewById<EditText>(R.id.signUpInputFantasia)
        val inputAtividade = findViewById<EditText>(R.id.signUpInputAtividade)
        val inputEmail = findViewById<EditText>(R.id.signUpInputEmail)
        val inputTelefone = findViewById<EditText>(R.id.signUpInputTelephone)
        val inputCEP = findViewById<EditText>(R.id.signUpInputCEP)
        val inputBairro = findViewById<EditText>(R.id.signUpInputBairro)
        val inputRua = findViewById<EditText>(R.id.signUpInputRua)
        val inputNumero = findViewById<EditText>(R.id.signUpInputNumero)
        val inputCidade = findViewById<EditText>(R.id.signUpInputCidade)
        val inputEstado = findViewById<EditText>(R.id.signUpInputEstado)
        val inputComplemento = findViewById<EditText>(R.id.signUpInputComplemento)
        val inputPassword = findViewById<EditText>(R.id.signUpInputPassword)
        val inputConfirmPassword = findViewById<EditText>(R.id.signUpInputConfirmPassword)
        val textViewPasswordRules = findViewById<TextView>(R.id.signUpPasswordRules)

        //Pegando o botão de cadastre-se e botão/texto de sign In
        val btnSignUp = findViewById<MaterialButton>(R.id.btnSignUp)
        val btnTxtSignIn = findViewById<TextView>(R.id.txtSignIn)

        //Função para fazer o REQUEST e gerar o autoComplet
        fun requestCNPJ (formatedCNPJ: String){
            val url = "https://www.receitaws.com.br/v1/cnpj/$formatedCNPJ"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    val empresaCadastro = Gson().fromJson(json, EmpresaCadastro::class.java)
                    val error = empresaCadastro.message

                    if (error.equals("CNPJ inválido")) {
                        runOnUiThread{
                            Toast.makeText(this@SignUpActivity, "CNPJ não encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        runOnUiThread {
                            inputFantasia.setText(empresaCadastro.fantasia)
                            val atividadePrincipalArray = empresaCadastro.atividade_principal
                            if (atividadePrincipalArray != null) {
                                if (atividadePrincipalArray.isNotEmpty()) {
                                    val primeiroObjeto = atividadePrincipalArray[0]
                                    val atividadePrincipal = primeiroObjeto.text
                                    inputAtividade.setText(atividadePrincipal)
                                }
                            }
                            inputEmail.setText(empresaCadastro.email)
                            inputTelefone.setText(empresaCadastro.telefone)
                            inputCEP.setText(empresaCadastro.cep)
                            inputBairro.setText(empresaCadastro.bairro)
                            inputCidade.setText(empresaCadastro.municipio)
                            inputEstado.setText(empresaCadastro.uf)
                            inputComplemento.setText(empresaCadastro.complemento)
                            inputRua.setText(empresaCadastro.logradouro)
                            inputNumero.setText(empresaCadastro.numero)
                        }
                    }
                }
            })
        }

        // função de focus off para quando o usuario terminar de digitar o CPF
        inputCNPJ.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                //Pegando os valores de todos os EditTex
                val stringCNPJ = inputCNPJ.text.toString()
                if (stringCNPJ.isNotEmpty()) {
                    //Formando o CNPJ para fazer o request : 01.123.456/0001-10 -> 01123456000110
                    val formatedCNPJ = stringCNPJ.replace("\\D".toRegex(), "")
                    requestCNPJ (formatedCNPJ)
                } else {
                    Toast.makeText(this, "Preencha o CNPJ corretamente!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Função para ir para tela de login
        btnTxtSignIn.setOnClickListener{
            val signInIntent = Intent(this, LoginActivity::class.java)
            startActivity(signInIntent)
        }

        //Função para desabilitar o checkbox da instituição se o usuario for doador
        doadorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            instituicaoCheckBox.isEnabled = !isChecked
            userType = "DONOR"
        }

        //Função para desabilitar o checkbox do doador se o usuario for instituição
        instituicaoCheckBox.setOnCheckedChangeListener { _, isChecked ->
            doadorCheckBox.isEnabled = !isChecked
            userType = "RECEIVER"
        }

        // Funtion to send the registration information
        fun sendRegistrationData(urlString: String, jsonData: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection

                // Set request method
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Write the JSON data to the request body
                val outputStream = connection.outputStream
                val writer = OutputStreamWriter(outputStream)
                writer.write(jsonData)
                writer.flush()
                writer.close()

                // Get the response from the server
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Request OK
                } else {
                    // Request failed
                    val errorMessage = when (responseCode) {
                        HttpURLConnection.HTTP_BAD_REQUEST -> "Invalid request. Please check your data."
                        HttpURLConnection.HTTP_UNAUTHORIZED -> "Unauthorized. Please login again."
                        HttpURLConnection.HTTP_FORBIDDEN -> "Forbidden. You don't have permission to access this resource."
                        HttpURLConnection.HTTP_NOT_FOUND -> "Resource not found."
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> "Internal server error."
                        else -> "Request failed with error code: $responseCode"
                    }
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                }

                connection.disconnect()
            }
        }

        //Função para confirmar o cadastro do usuario
        btnSignUp.setOnClickListener{

            //Pegando os textos de todos os inputs
            val stringCNPJ = inputCNPJ.text.toString().replace("\\D".toRegex(), "")
            val stringFantasia = inputFantasia.text.toString()
            val stringAtividade = inputAtividade.text.toString()
            val stringEmail = inputEmail.text.toString()
            val stringTelefone = inputTelefone.text.toString().replace("\\D".toRegex(), "")
            val stringCEP = inputCEP.text.toString()
            val stringBairro = inputBairro.text.toString()
            val stringRua = inputRua.text.toString()
            val stringNumero = inputNumero.text.toString()
            val stringCidade = inputCidade.text.toString()
            val stringEstado = inputEstado.text.toString()
            val stringComplemento = inputComplemento.text.toString()
            val stringPassword = inputPassword.text.toString()
            val stringConfirmPassword = inputConfirmPassword.text.toString()

            Log.d("User type:", userType)

            val inputStringList = listOf(
                stringCNPJ,
                stringFantasia,
                stringAtividade,
                stringEmail,
                stringTelefone,
                stringCEP,
                stringBairro,
                stringRua,
                stringNumero,
                stringCidade,
                stringEstado,
                stringComplemento,
                stringPassword,
                stringConfirmPassword,
                userType
            )

            //Função para requisitos minimos de senha
            fun validarSenha(stringPassword: String): Boolean {
                val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
                return regex.matches(stringPassword)
            }

            //Função para confirmação das senhas
            fun validarSenhasIguais(stringPassword: String, stringConfirmPassword: String): Boolean {
                return stringPassword == stringConfirmPassword
            }

            //Condicionais para validação do cadastro
            if (!doadorCheckBox.isChecked && !instituicaoCheckBox.isChecked) {
                Toast.makeText(this, "Favor informar se você é doador ou instituição", Toast.LENGTH_SHORT).show()
            } else if (inputStringList.any { it.isBlank() }) {
                Toast.makeText(this, "Favor preencher todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (!validarSenha(stringPassword)) {
                Toast.makeText(this, "Senha inválida!", Toast.LENGTH_SHORT).show()
                textViewPasswordRules.visibility = View.VISIBLE
            } else if (!validarSenhasIguais(stringPassword, stringConfirmPassword)) {
                Toast.makeText(this, "As senhas não são iguais!", Toast.LENGTH_SHORT).show()
                textViewPasswordRules.visibility = View.GONE
            } else {
                val url = "https://connect-food-back.onrender.com/user/register"
                val jsonData = """
                    {
                        "addresses": [
                            {
                                "bairro": "$stringBairro",
                                "cep": "$stringCEP",
                                "cidade": "$stringCidade",
                                "estado": "$stringEstado",
                                "numero": "$stringNumero",
                                "rua": "$stringRua"
                            }
                        ],
                        "cellphone": "$stringTelefone",
                        "cnpj": "$stringCNPJ",
                        "curtido": [0],
                        "description": "$stringAtividade",
                        "email": "$stringEmail",
                        "name": "$stringFantasia",
                        "password": "$stringPassword",
                        "phone": "$stringTelefone",
                        "photo": "https://img.freepik.com/free-icon/user_318-159711.jpg",
                        "type": "$userType"
                    }
                """.trimIndent()

                sendRegistrationData(url, jsonData)

                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}