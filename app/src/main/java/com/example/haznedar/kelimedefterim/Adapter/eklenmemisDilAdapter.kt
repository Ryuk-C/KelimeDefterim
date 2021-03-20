package com.example.haznedar.kelimedefterim.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Database.Diller
import com.example.haznedar.kelimedefterim.R
import com.squareup.picasso.Picasso

class eklenmemisDilAdapter(
    private val dillerListesi: java.util.ArrayList<Diller>,
) : RecyclerView.Adapter<eklenmemisDilAdapter.eklenmemisDilKartiTasarimTutucu>() {


    inner class eklenmemisDilKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var dil: TextView
        var dilResim: ImageView


        init {
            satirCardView = view.findViewById(R.id.eklenmemisDilCardView)
            dil = view.findViewById(R.id.eklenmemisDilAD)
            dilResim = view.findViewById(R.id.ivEklenmemisdilBayrak)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): eklenmemisDilAdapter.eklenmemisDilKartiTasarimTutucu {
        val tasarim = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_eklenmemis_dil_tasarim, parent, false)
        return eklenmemisDilKartiTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(
        holder: eklenmemisDilAdapter.eklenmemisDilKartiTasarimTutucu,
        position: Int
    ) {
        val veri = dillerListesi.get(position)

        holder.dil.text = veri.dil_isim

        val url = "http://haznedar.de/Haznedar/KelimeDefterim/Resimler/Bayraklar/${veri.dil_resim}"

        Picasso.get().load(url).into(holder.dilResim)

        holder.satirCardView.setOnClickListener {


        }
    }

    override fun getItemCount(): Int {
        return dillerListesi.size
    }

    fun update(newList: java.util.ArrayList<Diller>) {
        dillerListesi.clear()
        dillerListesi.addAll(newList)
        notifyDataSetChanged()
    }
}