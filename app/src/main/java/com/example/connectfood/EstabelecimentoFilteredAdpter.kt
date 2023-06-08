package com.example.connectfood

import android.widget.ImageView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class EstabelecimentoFilteredAdapter(private val estabelecimentosFiltered: List<EstabelecimentoFiltered>) :
    RecyclerView.Adapter<EstabelecimentoFilteredAdapter.EstabelecimentoFilteredViewHolder>() {

    // onCreateViewHolder is called when the RecyclerView needs a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstabelecimentoFilteredViewHolder {
        // Inflate the layout for the view holder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.estabelecimentos_filtered, parent, false)
        // Create and return a new view holder
        return EstabelecimentoFilteredViewHolder(itemView)
    }

    // onBindViewHolder is called to update the contents of a view holder
    override fun onBindViewHolder(holder: EstabelecimentoFilteredViewHolder, position: Int) {
        // Get the estabelecimento at the current position
        val estabelecimentoFiltered = estabelecimentosFiltered[position]
        // Update the view holder with the estabelecimento's information
        holder.bindView(estabelecimentoFiltered)
    }

    // getItemCount returns the number of items in the list
    override fun getItemCount(): Int {
        return estabelecimentosFiltered.size
    }

    // EstabelecimentoFilteredViewHolder is a view holder for a single estabelecimento item in the list
    class EstabelecimentoFilteredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Get references to the views in the view holder's layout
        private val logoImageView = itemView.findViewById<ImageView>(R.id.filteredUserLogo)
        private val nomeTextView = itemView.findViewById<TextView>(R.id.filteredUserName)
        private val dataTextView = itemView.findViewById<TextView>(R.id.filteredScheduledDate)
        private val distanciaTextView = itemView.findViewById<TextView>(R.id.filteredUserDistance)
        private val button = itemView.findViewById<TextView>(R.id.filteredBtn)
        private val finishedLayoutData = itemView.findViewById<LinearLayout>(R.id.filteredFinishedDateLayout)
        private val finishedData = itemView.findViewById<TextView>(R.id.filteredFinishedDate)
        private var visibility = false

        // bindView updates the views in the view holder with the information from the given estabelecimento
        fun bindView(estabelecimento: EstabelecimentoFiltered) {
            nomeTextView.text = estabelecimento.nome
            dataTextView.text = estabelecimento.dataAgendamento
            distanciaTextView.text = estabelecimento.distancia
            button.text = estabelecimento.txtBtn
            visibility = estabelecimento.visibility
            finishedData.text = estabelecimento.dataEncerramento

            if (!visibility) finishedLayoutData.visibility = View.VISIBLE else finishedLayoutData.visibility = View.GONE

            //Glide to set the img
            Glide.with(this.itemView)
                .load(estabelecimento.photo)
                .transform(RoundedCorners(25))
                .into(logoImageView)

            button.setOnClickListener {
                // ação a ser executada ao clicar no botão
            }
        }
    }
}
