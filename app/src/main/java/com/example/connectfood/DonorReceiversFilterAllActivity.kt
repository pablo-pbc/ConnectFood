package com.example.connectfood

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.*

@Suppress("DEPRECATION")
class DonorReceiversFilterAllActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var AllEstabelecimentos: List<EstabelecimentoAll>
    private lateinit var estabelecimentosFiltered: List<EstabelecimentoFiltered>

    //url image teste
    val imageUrl = "https://img.freepik.com/vetores-premium/modelo-de-design-de-logotipo-de-restaurante_79169-56.jpg?w=2000"
    val imageUrlFiltered = "https://www.designevo.com/res/templates/thumb_small/restaurant-menu-logo.webp"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recivers_filter_all_screen)

        //Getting elements from current screen
        val filterAllBtn = findViewById<TextView>(R.id.filterAll)
        val filterScheduleBtn = findViewById<TextView>(R.id.filterSchedule)
        val filterFinishedBtn = findViewById<TextView>(R.id.filterFinished)

        //Setting variables font styles
        val robotoRegular = resources.getFont(R.font.roboto_regular)
        val robotoBold = resources.getFont(R.font.roboto_bold)

        //Getting button layout grid
        val listBtn = findViewById<MaterialButton>(R.id.filterAllTypeList)
        val gridBtn = findViewById<MaterialButton>(R.id.filterAllTypeGrid)
        val filterAllMainLayout = findViewById<LinearLayout>(R.id.filterAllMainLayout)

        //Getting the recyclerView from screen
        recyclerView = findViewById(R.id.recyclerView)
        //Setting the layout grid with 2 columns -> Standard mode
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        //Function that return the query for all donors or recipients
        fun allDonorReciverList () {
            AllEstabelecimentos = listOf(
                EstabelecimentoAll("Restaurante 1", "Comida boa", "1 km", imageUrl),
                EstabelecimentoAll("Restaurante 2", "Comida barata", "2 km", imageUrlFiltered),
                EstabelecimentoAll("Restaurante 3", "Comida gostosa", "3 km", imageUrl),
                EstabelecimentoAll("Restaurante 4", "Comida rápida", "4 km", imageUrl),
                EstabelecimentoAll("Restaurante 5", "Comida saudável", "5 km", imageUrl)
            )

            //Setting the layout grid with 2 columns -> Standard mode
            recyclerView.layoutManager = GridLayoutManager(this, 2)

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
        fun filteredDonorReciverList (filterType: String) {

            val btnTxt: String = if (filterType == "scheduled") "Finalizar" else ""
            val finishedData: Boolean = filterType == "scheduled"

            recyclerView.layoutManager = GridLayoutManager(this, 1)

            estabelecimentosFiltered = listOf(
                EstabelecimentoFiltered("Restaurante 1", "15/05/23", null, "1 km", imageUrlFiltered, btnTxt, finishedData),
                EstabelecimentoFiltered("Restaurante 2", "15/05/23", null, "2 km", imageUrlFiltered, btnTxt, finishedData),
                EstabelecimentoFiltered("Restaurante 3", "15/05/23", null, "3 km", imageUrlFiltered, btnTxt, finishedData),
                EstabelecimentoFiltered("Restaurante 4", "15/05/23", null, "4 km", imageUrlFiltered, btnTxt, finishedData),
                EstabelecimentoFiltered("Restaurante 5", "15/05/23", null, "5 km", imageUrlFiltered, btnTxt, finishedData)
            )

            val adapterFiltered = EstabelecimentoFilteredAdapter(estabelecimentosFiltered)
            recyclerView.adapter = adapterFiltered

        }

        //Setting click function to filterAllBtn
        filterAllBtn.setOnClickListener {
            //Calling the function
            allDonorReciverList ()
            filterAllMainLayout.visibility = View.VISIBLE

            //Formatting filter button
            filterAllBtn.typeface = robotoBold

            filterScheduleBtn.typeface = robotoRegular
            filterScheduleBtn.paintFlags = 0

            filterFinishedBtn.typeface = robotoRegular
            filterFinishedBtn.paintFlags = 0
        }

        //Setting click function to filterScheduleBtn
        filterScheduleBtn.setOnClickListener {
            //Calling the function and giving specifics params
            filteredDonorReciverList ("scheduled")
            filterAllMainLayout.visibility = View.GONE

            //Formatting filter button
            filterAllBtn.typeface = robotoRegular
            filterAllBtn.paintFlags = 0

            filterScheduleBtn.typeface = robotoBold
            filterScheduleBtn.paintFlags = 1

            filterFinishedBtn.typeface = robotoRegular
            filterFinishedBtn.paintFlags = 0
        }

        //Setting click function to filterFinishedBtn
        filterFinishedBtn.setOnClickListener {
            //Calling the function and giving specifics params
            filteredDonorReciverList ("finished")
            filterAllMainLayout.visibility = View.GONE

            //Formatting filter button
            filterAllBtn.typeface = robotoRegular
            filterAllBtn.paintFlags = 0

            filterFinishedBtn.typeface = robotoBold
            filterFinishedBtn.paintFlags = 1

            filterScheduleBtn.typeface = robotoRegular
            filterScheduleBtn.paintFlags = 0
        }

        //Standard function of the screen
        allDonorReciverList ()
    }
}
