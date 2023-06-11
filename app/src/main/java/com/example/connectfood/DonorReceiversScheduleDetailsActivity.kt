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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.santalu.maskara.widget.MaskEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.time.LocalDate

@Suppress("DEPRECATION")
class DonorReceiversScheduleDetailsActivity : AppCompatActivity() {

    data class Address(
        val id: Int,
        val estado: String,
        val cidade: String,
        val bairro: String,
        val rua: String,
        val numero: String,
        val cep: String
    )

    data class Company(
        val id: Int,
        val name: String,
        val description: String,
        val addresses: List<Address>,
        val cellphone: String,
        val phone: String
    )

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_schedule_details_screen)

        //Getting elements from current screen
        val backButton = findViewById<ImageView>(R.id.backArrow)
        val likeIcon = findViewById<ImageView>(R.id.likeOrUnlikedIcon)
        val banner = findViewById<ImageView>(R.id.scheduleDetailsNoLoggedUserBanner)
        val editTextData = findViewById<MaskEditText>(R.id.scheduleDetailsDeliveryDate)
        val userNoLoggedDataLayout = findViewById<LinearLayout>(R.id.scheduleDataLayout)
        val userNoLoggedName = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserName)
        val confirmButton = findViewById<MaterialButton>(R.id.scheduleDetailsConfirmScheduleBtn)
        val userNoLoggedAddress = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserAdress)
        val userNoLoggedPhone = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserTelephone)
        val userNoLoggedWhatsapp = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserWhatsapp)
        val userNoLoggedSlogan = findViewById<TextView>(R.id.scheduleDetailsNoLoggedUserDescription)

        val loggedUserPhoto = findViewById<ImageView>(R.id.scheduleDetailsLoggedUserLogo)
        val loggedUserName = findViewById<TextView>(R.id.scheduleDetailsLoggedUserName)
        val loggedUserLocation = findViewById<TextView>(R.id.scheduleDetailsLoggedUserLocation)

        //Getting val from intent NO LOGGED USER
        val cnpjNoLogged = intent.getStringExtra("cnpjNoLogged")
        var isLiked = intent.getStringExtra("isLiked")
        val intentImageUrl = intent.getStringExtra("urlImage")
        val noLoggedUserName = intent.getStringExtra("noLoggedUserName")
        val noLoggedUserSlogan = intent.getStringExtra("noLoggedUserSlogan")

        // Getting val from intent LOGGED USER
        val loggedUserIdExtra = intent.getStringExtra("loggedUserId")
        val loggedUserTypeExtra = intent.getStringExtra("loggedUserType")
        val loggedUserNameExtra = intent.getStringExtra("loggedUserName")
        val loggedUserPhotoExtra = intent.getStringExtra("loggedUserPhoto")
        val loggedUserLocationExtra = intent.getStringExtra("loggedUserLocation")

        loggedUserName.text = loggedUserNameExtra
        loggedUserLocation.text = loggedUserLocationExtra

        userNoLoggedName.text = noLoggedUserName
        userNoLoggedSlogan.text = noLoggedUserSlogan

        // Use Glide to load the image
        Glide.with(this)
            .load(intentImageUrl)
            .transform(RoundedCorners(25))
            .into(banner)

        // Use Glide to load the image
        Glide.with(this)
            .load(loggedUserPhotoExtra)
            .transform(RoundedCorners(25))
            .into(loggedUserPhoto)

        backButton.setOnClickListener(){
            val filterAll = Intent(this, DonorReceiversFilterAllActivity::class.java)
            startActivity(filterAll)
        }

        if (isLiked == "true") {
            likeIcon.setImageResource(R.drawable.liked)
        } else {
            likeIcon.setImageResource(R.drawable.not_liked)
        }

        //Function to change the like or unliked icon
        likeIcon.setOnClickListener() {
            println("clicado")
            if (isLiked === "false") {
                likeIcon.setImageResource(R.drawable.liked)
                //COLOCAR AQUI A QUERY PRO BANCO
                isLiked = "true"
            } else {
                likeIcon.setImageResource(R.drawable.not_liked)
                //COLOCAR AQUI A QUERY PRO BANCO
                isLiked = "false"
            }
        }

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
                        //val jsonResponse = connection.inputStream.bufferedReader().use { it.readText() }
                        //val jsonObject = JSONObject(jsonResponse)

                        runOnUiThread {
                            Toast.makeText(applicationContext, "Agendamento realizado com sucesso!", Toast.LENGTH_SHORT).show()

                            val helloScreen = Intent(applicationContext, DonorReceiversFilterAllActivity::class.java)
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

        //Getting the no logged user's information
        fun GetdetailsNoLoggedUser (){
            val url = "https://connect-food-back.onrender.com/user/cnpj/$cnpjNoLogged"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    val gson = Gson()
                    val company: Company = gson.fromJson(json, Company::class.java)
                    val addresses: List<Address> = company.addresses
                    val cellphone: String = company.cellphone
                    val phone: String = company.phone

                    "${addresses[0].rua}, ${addresses[0].numero} - ${addresses[0].bairro}\n${addresses[0].estado} - ${addresses[0].cep}".also { userNoLoggedAddress.text = it }
                    userNoLoggedWhatsapp.text = cellphone
                    userNoLoggedPhone.text = phone

                    confirmButton.setOnClickListener() {
                        var confirmTxt = "Confirmar agendamento"
                        var finishTxt = "Finalizar agendamento"
                        var receiverID: String
                        var donorID: String

                        val dataAtual = LocalDate.now()

                        if (confirmButton.text == confirmTxt) {
                            confirmButton.text = finishTxt
                            userNoLoggedDataLayout.visibility = View.VISIBLE
                        } else {

                            if (loggedUserTypeExtra.equals("DONOR")) {
                                donorID = loggedUserIdExtra.toString()
                                receiverID = company.id.toString()
                            } else {
                                receiverID = loggedUserIdExtra.toString()
                                donorID = company.id.toString()
                            }

                            println(dataAtual)
                            println("${editTextData.text}")

                            val jsonData = """
                                {
                                  "dataAgendamento": "$dataAtual",
                                  "dataFinalizado": "${editTextData.text}",
                                  "descricao": "Agendamento entre $loggedUserNameExtra e $noLoggedUserName",
                                  "doacaoFinalizada": false,
                                    "instituicao": {
                                    "id": $receiverID
                                  },
                                  "restaurante": {
                                    "id": $donorID
                                  }
                                }
                            """.trimIndent()

                            val url = "https://connect-food-back.onrender.com/doacao"

                            sendValidateLogin(url, jsonData)
                        }
                    }

                }
            })
        }

        GetdetailsNoLoggedUser()
    }
}
