package com.example.connectfood

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.*

@Suppress("DEPRECATION")
class DonorRecipientsFilterAllActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var AllEstabelecimentos: List<EstabelecimentoAll>
    private lateinit var estabelecimentosFiltered: List<EstabelecimentoFiltered>

    //url image teste
    val imageUrl = "https://img.freepik.com/vetores-premium/modelo-de-design-de-logotipo-de-restaurante_79169-56.jpg?w=2000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_filter_all_screen)

        //Getting elements from current screen
        val filterAllBtn = findViewById<TextView>(R.id.filterAll)
        val filterScheduleBtn = findViewById<TextView>(R.id.filterSchedule)
        val filterFinishedBtn = findViewById<TextView>(R.id.filterFinished)

        //Getting button layout grid
        val listBtn = findViewById<MaterialButton>(R.id.filterAllTypeList)
        val gridBtn = findViewById<MaterialButton>(R.id.filterAllTypeGrid)
        val filterAllMainLayout = findViewById<LinearLayout>(R.id.filterAllMainLayout)

        //Getting the recyclerView from screen
        recyclerView = findViewById(R.id.recyclerView)
        //Setting the layout grid with 2 columns -> Standard mode
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        //Function that return the query for all donors or recipients
        fun allDonorRecipientList () {
            AllEstabelecimentos = listOf(
                EstabelecimentoAll("Restaurante 1", "Comida boa", "1 km", imageUrl),
                EstabelecimentoAll("Restaurante 2", "Comida barata", "2 km", imageUrl),
                EstabelecimentoAll("Restaurante 3", "Comida gostosa", "3 km", imageUrl),
                EstabelecimentoAll("Restaurante 4", "Comida rápida", "4 km", imageUrl),
                EstabelecimentoAll("Restaurante 5", "Comida saudável", "5 km", imageUrl)
            )

            // Creating the adapter variable and its definition as adapter from RecycleView
            val adapter = EstabelecimentoAllAdapter(AllEstabelecimentos)
            recyclerView.adapter = adapter

            //Function to set the number of display columns as LIST
            listBtn.setOnClickListener {
                listBtn.setBackgroundColor(Color.parseColor("#F48C06"))
                gridBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
                recyclerView.layoutManager = GridLayoutManager(this, 1)
            }

            //Function to set the number of display columns as GRID
            gridBtn.setOnClickListener {
                gridBtn.setBackgroundColor(Color.parseColor("#F48C06"))
                listBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }
        }

        //Function that return the query of donations scheduled or finished
        fun filteredDonorRecipientList (filterType: String) {
            var btnTxt: String = ""

            btnTxt = if (filterType == "scheduled") {
                "Finalizar"
            } else {
                ""
            }

            estabelecimentosFiltered = listOf(
                EstabelecimentoFiltered("Restaurante 1", "15/05/23", "1 km", imageUrl, btnTxt),
                EstabelecimentoFiltered("Restaurante 2", "15/05/23", "2 km", imageUrl, btnTxt),
                EstabelecimentoFiltered("Restaurante 3", "15/05/23", "3 km", imageUrl, btnTxt),
                EstabelecimentoFiltered("Restaurante 4", "15/05/23", "4 km", imageUrl, btnTxt),
                EstabelecimentoFiltered("Restaurante 5", "15/05/23", "5 km", imageUrl, btnTxt)
            )
        }

        //Setting click function to filterAllBtn
        filterAllBtn.setOnClickListener {
            //Calling the function
            allDonorRecipientList ()
        }

        //Setting click function to filterScheduleBtn
        filterScheduleBtn.setOnClickListener {
            //Calling the function and giving specifics params
            filteredDonorRecipientList ("scheduled")
        }

        //Setting click function to filterFinishedBtn
        filterFinishedBtn.setOnClickListener {
            //Calling the function and giving specifics params
            filteredDonorRecipientList ("finished")
        }

        //Standard function of the screen
        allDonorRecipientList ()
    }
}
