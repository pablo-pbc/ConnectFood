package com.example.connectfood

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException


@Suppress("DEPRECATION")
class DonorReceiversFilterAllActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var estabelecimentosFiltered: List<EstabelecimentoFiltered>

    // Variáveis para elementos da tela atual
    private lateinit var filterAllLoggedUserLogo: ImageView
    private lateinit var filterAllLoggedUserName: TextView
    private lateinit var locationTextView: TextView

    // Declaração da variável scheduleTypeUsers fora da função
    private val scheduleTypeUsers = mutableListOf<EstabelecimentoFiltered>()
    private var filteredList = mutableListOf<EstabelecimentoFiltered>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_filter_all_screen)

        // Obtendo variáveis da tela anterior
        val cnpj = intent.getStringExtra("cnpj")
        val endereco = intent.getStringExtra("endereco")
        val nome = intent.getStringExtra("nome")
        val photo = intent.getStringExtra("photo")
        val type = intent.getStringExtra("type")

        // Obtendo elementos da tela atual
        filterAllLoggedUserLogo = findViewById(R.id.filterAllLoggedUserLogo)
        filterAllLoggedUserName = findViewById(R.id.filterAllLoggedUserName)
        locationTextView = findViewById(R.id.locationTextView)

        // Definindo seus valores com base nas informações do usuário
        filterAllLoggedUserName.text = nome
        locationTextView.text = endereco
        // Glide para definir a imagem
        Glide.with(this)
            .load(photo)
            .into(filterAllLoggedUserLogo)

        // Obtendo elementos da tela atual
        val filterAllBtn = findViewById<TextView>(R.id.filterAll)
        val filterScheduleBtn = findViewById<TextView>(R.id.filterSchedule)
        val filterFinishedBtn = findViewById<TextView>(R.id.filterFinished)

        // Definindo estilos de fonte para as variáveis
        val robotoRegular = resources.getFont(R.font.roboto_regular)
        val robotoBold = resources.getFont(R.font.roboto_bold)

        // Obtendo botões de layout de grade
        val listBtn = findViewById<MaterialButton>(R.id.filterAllTypeList)
        val gridBtn = findViewById<MaterialButton>(R.id.filterAllTypeGrid)
        val filterAllMainLayout = findViewById<LinearLayout>(R.id.filterAllMainLayout)

        // Obtendo o recyclerView da tela
        recyclerView = findViewById(R.id.recyclerView)
        // Definindo o layout de grade com 2 colunas -> modo padrão
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Função que retorna a lista de doadores ou destinatários
        fun allDonorReceiverList() {
            val url = "https://connect-food-back.onrender.com/user/get-distance/$cnpj"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            val nearbyUsers = mutableListOf<EstabelecimentoAll>()
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Lidando com a falha na solicitação
                    runOnUiThread {
                        Toast.makeText(this@DonorReceiversFilterAllActivity, "Internal Server Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    val jsonString = """$json""".trimIndent()
                    val jsonObject = JSONObject(jsonString)

                    val keys = jsonObject.keys()
                    coroutineScope.launch {
                        val deferredList = mutableListOf<Deferred<EstabelecimentoAll?>>()

                        while (keys.hasNext()) {
                            val key = keys.next() as String
                            val distance = jsonObject.getString(key)
                            val numericKey = key.toLong()

                            val numericDistance = distance.split(" ")[0].toDoubleOrNull() // Extrai o valor numérico da distância como Double

                            // Comparação com uma distância X
                            val distanceRadius = 40.0 // Distância de referência
                            if (numericDistance != null && numericDistance > distanceRadius) {
                                val urlUser = "https://connect-food-back.onrender.com/user/cnpj/$numericKey"
                                val requestUser = Request.Builder().url(urlUser).build()
                                val clientUser = OkHttpClient()

                                val deferred = coroutineScope.async(Dispatchers.IO) {
                                    val responseUser = clientUser.newCall(requestUser).execute()
                                    if (responseUser.isSuccessful) {
                                        val responseUserString = responseUser.body?.string()
                                        if (!responseUserString.isNullOrEmpty()) {
                                            val gson = Gson()
                                            val user = gson.fromJson(responseUserString, EstabelecimentoAll::class.java)
                                            user.distance = distance
                                            user
                                        } else {
                                            null
                                        }
                                    } else {
                                        null
                                    }
                                }
                                deferredList.add(deferred)
                            }
                        }

                        val results = deferredList.awaitAll()
                        results.filterNotNullTo(nearbyUsers)

                        // Depois de obter os dados, configurar o adaptador e atribuí-lo ao recyclerView
                        runOnUiThread {
                            // Definindo o layout de grade com 2 colunas -> modo padrão
                            recyclerView.layoutManager = GridLayoutManager(this@DonorReceiversFilterAllActivity, 2)
                            val adapter = EstabelecimentoAllAdapter(nearbyUsers)
                            recyclerView.adapter = adapter
                        }
                    }
                }
            })
        }

        // Função que retorna a lista filtrada de doadores ou destinatários
        fun filteredDonorReceiverList(filterType: String) {
            val scheduleTypeUsers = mutableListOf<EstabelecimentoFiltered>()
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            val btnTxt: String = if (filterType == "scheduled") "Finalizar" else ""
            val finishedData: Boolean = filterType == "scheduled"

            val urlDonor = "https://connect-food-back.onrender.com/doacao/get-by-cnpj/$cnpj"
            val requestDonor = Request.Builder().url(urlDonor).build()
            val clientDonor = OkHttpClient()

            clientDonor.newCall(requestDonor).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Lidando com a falha na solicitação
                    runOnUiThread {
                        Toast.makeText(this@DonorReceiversFilterAllActivity, "Internal Server Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonString = response.body?.string()

                    try {
                        val jsonArray = JSONArray(jsonString)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val doacaoFinalizada = jsonObject.getBoolean("doacaoFinalizada")
                            val restaurante = jsonObject.getJSONObject("restaurante")
                            val name = restaurante.getString("name")
                            val dataAgendamento = jsonObject.getString("dataAgendamento")
                            val dataFinalizado = jsonObject.getString("dataFinalizado")
                            val photo = restaurante.getString("photo")

                            if ((filterType == "scheduled" && !doacaoFinalizada) || (filterType == "finished" && doacaoFinalizada)) {
                                val estabelecimentoFiltered = EstabelecimentoFiltered(name, dataAgendamento, dataFinalizado, photo, btnTxt, finishedData, doacaoFinalizada.toString())
                                scheduleTypeUsers.add(estabelecimentoFiltered)
                            }
                        }

                        runOnUiThread {
                            // Definindo o layout de grade com 2 colunas -> modo padrão
                            recyclerView.layoutManager = GridLayoutManager(this@DonorReceiversFilterAllActivity, 1)
                            val adapter = EstabelecimentoFilteredAdapter(scheduleTypeUsers)
                            recyclerView.adapter = adapter
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@DonorReceiversFilterAllActivity, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }



        // Função para definir o número de colunas de exibição como LISTA
        listBtn.setOnClickListener {
            listBtn.setBackgroundColor(Color.parseColor("#F48C06"))
            gridBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
            recyclerView.layoutManager = GridLayoutManager(this, 1)
        }

        // Função para definir o número de colunas de exibição como GRADE
        gridBtn.setOnClickListener {
            gridBtn.setBackgroundColor(Color.parseColor("#F48C06"))
            listBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }

        // Definindo a função de clique para filterAllBtn
        filterAllBtn.setOnClickListener {
            // Chamando a função para exibir todos os doadores ou destinatários
            allDonorReceiverList()
            filterAllMainLayout.visibility = View.VISIBLE

            // Formatação do botão de filtro
            filterAllBtn.typeface = robotoBold

            filterScheduleBtn.typeface = robotoRegular
            filterScheduleBtn.paintFlags = 0

            filterFinishedBtn.typeface = robotoRegular
            filterFinishedBtn.paintFlags = 0
        }

        // Definindo a função de clique para filterScheduleBtn
        filterScheduleBtn.setOnClickListener {
            // Chamando a função para exibir doadores ou destinatários filtrados (agendados)
            filteredDonorReceiverList("scheduled")
            filterAllMainLayout.visibility = View.GONE

            // Formatação do botão de filtro
            filterAllBtn.typeface = robotoRegular
            filterAllBtn.paintFlags = 0

            filterScheduleBtn.typeface = robotoBold
            filterScheduleBtn.paintFlags = 1

            filterFinishedBtn.typeface = robotoRegular
            filterFinishedBtn.paintFlags = 0
        }

        // Definindo a função de clique para filterFinishedBtn
        filterFinishedBtn.setOnClickListener {
            // Chamando a função para exibir doadores ou destinatários filtrados (concluídos)
            filteredDonorReceiverList("finished")
            filterAllMainLayout.visibility = View.GONE

            // Formatação do botão de filtro
            filterAllBtn.typeface = robotoRegular
            filterAllBtn.paintFlags = 0

            filterFinishedBtn.typeface = robotoBold
            filterFinishedBtn.paintFlags = 1
            filterScheduleBtn.typeface = robotoRegular
            filterScheduleBtn.paintFlags = 0

            // Atualizar a lista filtrada
            filteredList = scheduleTypeUsers.filter { it.doacaoFinalizada.toBoolean() == true }.toMutableList()

            // Exibir a lista filtrada no RecyclerView
            recyclerView.adapter?.notifyDataSetChanged()
        }

        // Função padrão da tela
        allDonorReceiverList()
    }
}
