package com.example.connectfood

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.*

@Suppress("DEPRECATION")
class DonorRecipientsFilterAllActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var estabelecimentos: List<Estabelecimento>
    //private lateinit var layoutWidthType: EstabelecimentoAdapter.EstabelecimentoViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_recipients_filter_all_screen)

        //url image teste
        val imageUrl = "https://img.freepik.com/vetores-premium/modelo-de-design-de-logotipo-de-restaurante_79169-56.jpg?w=2000"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        estabelecimentos = listOf(
            Estabelecimento("Restaurante 1", "Comida boa", "1 km", imageUrl),
            Estabelecimento("Restaurante 2", "Comida barata", "2 km", imageUrl),
            Estabelecimento("Restaurante 3", "Comida gostosa", "3 km", imageUrl),
            Estabelecimento("Restaurante 4", "Comida rápida", "4 km", imageUrl),
            Estabelecimento("Restaurante 5", "Comida saudável", "5 km", imageUrl)
        )

        // Criar o adapter e definir como adapter do RecyclerView
        val adapter = EstabelecimentoAdapter(estabelecimentos)
        recyclerView.adapter = adapter

        //Getting button layout grid
        val listBtn = findViewById<MaterialButton>(R.id.filterAllTypeList)
        val gridBtn = findViewById<MaterialButton>(R.id.filterAllTypeGrid)

        listBtn.setOnClickListener {
            listBtn.setBackgroundColor(Color.parseColor("#F48C06"))
            gridBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
            recyclerView.layoutManager = GridLayoutManager(this, 1)
        }

        gridBtn.setOnClickListener {
            gridBtn.setBackgroundColor(Color.parseColor("#F48C06"))
            listBtn.setBackgroundColor(Color.parseColor("#FFBB63"))
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }

    }
}
