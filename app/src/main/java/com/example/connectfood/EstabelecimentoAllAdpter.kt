package com.example.connectfood

import android.content.Intent
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class EstabelecimentoAllAdapter(private val estabelecimentos: List<EstabelecimentoAll>) :
    RecyclerView.Adapter<EstabelecimentoAllAdapter.EstabelecimentoViewHolder>() {

    // Interface for button click events
    interface OnContatoButtonClickListener {
        fun onContatoButtonClick(estabelecimento: EstabelecimentoAll)
    }

    private var onContatoButtonClickListener: OnContatoButtonClickListener? = null

    // onCreateViewHolder is called when the RecyclerView needs a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstabelecimentoViewHolder {
        // Inflate the layout for the view holder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.estabelecimentos_grid, parent, false)
        // Create and return a new view holder
        return EstabelecimentoViewHolder(itemView)
    }

    // onBindViewHolder is called to update the contents of a view holder
    override fun onBindViewHolder(holder: EstabelecimentoViewHolder, position: Int) {
        // Get the estabelecimento at the current position
        val estabelecimento = estabelecimentos[position]
        // Update the view holder with the estabelecimento's information
        holder.bindView(estabelecimento)
    }

    // getItemCount returns the number of items in the list
    override fun getItemCount(): Int {
        return estabelecimentos.size
    }

    // EstabelecimentoViewHolder is a view holder for a single estabelecimento item in the list
    class EstabelecimentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logoImageView = itemView.findViewById<ImageView>(R.id.filterAllNoLoggedUserLogo)
        private val nomeTextView = itemView.findViewById<TextView>(R.id.filterAllNoLoggedUserName)
        private val sloganTextView = itemView.findViewById<TextView>(R.id.filterAllNoLoggedUserSlogan)
        private val distanciaTextView = itemView.findViewById<TextView>(R.id.filterAllNoLoggedUserDistance)
        private val contatoButton = itemView.findViewById<TextView>(R.id.filterAllNoLoggedContactBtn)
        private val likeBtn = itemView.findViewById<ImageView>(R.id.filterAllNoLoggedLikeIcon)
        var isLiked = "false"

        fun bindView(estabelecimento: EstabelecimentoAll) {
            nomeTextView.text = estabelecimento.name
            sloganTextView.text = estabelecimento.description
            distanciaTextView.text = estabelecimento.distance

            // Use Glide to load the image
            Glide.with(itemView)
                .load(estabelecimento.photo)
                .transform(RoundedCorners(50))
                .into(logoImageView)

            //Function to change the like or unliked icon
            likeBtn.setOnClickListener() {
                if (isLiked === "false") {
                    likeBtn.setImageResource(R.drawable.liked)
                    //COLOCAR AQUI A QUERY PRO BANCO
                    isLiked = "true"
                } else {
                    likeBtn.setImageResource(R.drawable.not_liked)
                    //COLOCAR AQUI A QUERY PRO BANCO
                    isLiked = "false"
                }
            }

            // Handle button click event
            contatoButton.setOnClickListener {
                // Going to the next screen
                val details = Intent(itemView.context, DonorReceiversScheduleDetailsActivity::class.java)
                details.putExtra("urlImage", estabelecimento.photo)
                details.putExtra("isLiked", isLiked)
                details.putExtra("noLoggedUserName", estabelecimento.name)
                details.putExtra("noLoggedUserSlogan", estabelecimento.description)
                itemView.context.startActivity(details)
            }
        }
    }
}
