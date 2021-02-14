package com.example.haznedar.kelimedefterim.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Database.Kelimeler
import com.example.haznedar.kelimedefterim.R

class kelimelerAdapter(private val kelimelerListesi: java.util.ArrayList<Kelimeler>)
    : RecyclerView.Adapter<kelimelerAdapter.kelimeKartiTasarimTutucu>() {

    inner class kelimeKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var anaKelime: TextView
        var kelimeKarsilik: TextView

        init {
            satirCardView = view.findViewById(R.id.cardViewListe)
            anaKelime = view.findViewById(R.id.tvAnaKelime)
            kelimeKarsilik = view.findViewById(R.id.tvkelimeKarsilik)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kelimeKartiTasarimTutucu {


        if (kelimelerListesi.size <1){

            val tasarim = LayoutInflater.from(parent.context).inflate(R.layout.wordblank, parent, false)
            return kelimeKartiTasarimTutucu(tasarim)


        }else{

            val tasarim = LayoutInflater.from(parent.context).inflate(R.layout.card_liste_tasarim, parent, false)
            return kelimeKartiTasarimTutucu(tasarim)
        }

    }

    override fun onBindViewHolder(holder: kelimeKartiTasarimTutucu, position: Int) {

        val veri = kelimelerListesi.get(position)

        holder.anaKelime.text = veri.ana_kelime
        holder.kelimeKarsilik.text = veri.kelime_karsilik

        holder.satirCardView.setOnClickListener {



        }
    }

    override fun getItemCount(): Int {
        return kelimelerListesi.size

    }

    fun update(newList: java.util.ArrayList<Kelimeler>) {
        kelimelerListesi.clear()
        kelimelerListesi.addAll(newList)
        notifyDataSetChanged()
    }
}