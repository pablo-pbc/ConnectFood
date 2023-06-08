package com.example.connectfood

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginInput = findViewById<EditText>(R.id.signInLoginInput)
        val passwordInput = findViewById<EditText>(R.id.signInPasswordInput)

        fun sendValidateLogin(urlString: String, jsonData: String) {
            GlobalScope.launch(Dispatchers.IO) {

                var connection: HttpURLConnection? = null

                try {
                    val url = URL(urlString)
                    connection = url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val outputStream = connection.outputStream
                    val writer = OutputStreamWriter(outputStream)
                    writer.write(jsonData)
                    writer.flush()
                    writer.close()

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val jsonResponse = connection.inputStream.bufferedReader().use { it.readText() }
                        val jsonObject = JSONObject(jsonResponse)

                        val cnpj = jsonObject.getString("cnpj")
                        val endereco = jsonObject.getString("endereco")
                        val nome = jsonObject.getString("nome")
                        val photo = jsonObject.getString("photo")
                        val description = jsonObject.getString("description")

                        val enderecoSemDetalhes = endereco.substringBefore("-").trim()

                        runOnUiThread {
                            Toast.makeText(applicationContext, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                            val helloScreen = Intent(applicationContext, DonorReceiversHelloScrActivity::class.java)
                            helloScreen.putExtra("cnpj", cnpj)
                            helloScreen.putExtra("endereco", enderecoSemDetalhes)
                            helloScreen.putExtra("nome", nome)
                            helloScreen.putExtra("photo", photo)
                            helloScreen.putExtra("description", description)
                            startActivity(helloScreen)
                            finish()
                        }
                    } else {
                        val errorMessage = when (responseCode) {
                            HttpURLConnection.HTTP_BAD_REQUEST -> "Invalid request. Please check your data."
                            HttpURLConnection.HTTP_UNAUTHORIZED -> "Unauthorized. Please login again."
                            HttpURLConnection.HTTP_FORBIDDEN -> "Forbidden. You don't have permission to access this resource."
                            HttpURLConnection.HTTP_NOT_FOUND -> "Resource not found."
                            HttpURLConnection.HTTP_INTERNAL_ERROR -> "Verifique seus dados de acesso!"
                            else -> "Request failed with error code: $responseCode"
                        }
                        runOnUiThread {
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(applicationContext, "An error occurred while processing the request.", Toast.LENGTH_SHORT).show()
                    }
                } finally {
                    connection?.disconnect()
                }
            }
        }

        val signInBtn = findViewById<Button>(R.id.signInBtn)
        signInBtn.setOnClickListener {
            val inputedLogin = loginInput.text.toString()
            val password = passwordInput.text.toString()

            // Formatting the user login 01.123.456/0001-10 -> 01123456000110
            val formattedLogin = inputedLogin.replace(".", "").replace("/", "").replace("-", "")

            val jsonData = """
                {
                    "cnpj": "$formattedLogin",
                    "password": "$password",
                    "type": ""
                }
                """.trimIndent()

            val url = "https://connect-food-back.onrender.com/user/login"

            sendValidateLogin(url, jsonData)
        }

        val signUpBtn = findViewById<TextView>(R.id.signInSignUpBtn)
        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
