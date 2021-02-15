package com.example.haznedar.kelimedefterim.Adapter

import android.util.Log
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

class yeniKelimeAdapter(
    private val dillerListesi: java.util.ArrayList<Diller>,
    private val listener: yeniKelimeAdapter.OnItemClickListener
) : RecyclerView.Adapter<yeniKelimeAdapter.yeniKelimeKartiTasarimTutucu>() {


    interface OnItemClickListener {

        fun OnItemClicked(id: Int)

    }


    inner class yeniKelimeKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var dil: TextView
        var dilResim: ImageView


        init {
            satirCardView = view.findViewById(R.id.cardViewDilEkle)
            dil = view.findViewById(R.id.tvYeniKelimeDil)
            dilResim = view.findViewById(R.id.ivYeniKelimeResim)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): yeniKelimeKartiTasarimTutucu {
        val tasarim = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_yeni_kelime_dil, parent, false)
        return yeniKelimeKartiTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: yeniKelimeKartiTasarimTutucu, position: Int) {

        val veri = dillerListesi.get(position)

        holder.dil.text = veri.dil_isim

        val url = "http://haznedar.de/Haznedar/KelimeDefterim/Resimler/Bayraklar/${veri.dil_resim}"

        Picasso.get().load(url).into(holder.dilResim)

        holder.satirCardView.setOnClickListener {


            listener.OnItemClicked(veri.dil_id)

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