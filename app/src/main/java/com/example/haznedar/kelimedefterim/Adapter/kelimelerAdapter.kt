package com.example.haznedar.kelimedefterim.Adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Activity.ListelerimFragment
import com.example.haznedar.kelimedefterim.Database.Kelimeler
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.interfacee.KelimeSecInterface
import kotlinx.android.synthetic.main.dialog_delete_kelime.view.*

class kelimelerAdapter(private val kelimelerListesi: java.util.ArrayList<Kelimeler>,
                       private val kelimeSecInterface : KelimeSecInterface

                       ):
    RecyclerView.Adapter<kelimelerAdapter.kelimeKartiTasarimTutucu>() {

    inner class kelimeKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var anaKelime: TextView
        var kelimeKarsilik: TextView
        var toplamKelime: TextView?
        var kelimeSilButon : ImageView


        init {
            satirCardView = view.findViewById(R.id.cardViewListe)
            anaKelime = view.findViewById(R.id.tvAnaKelime)
            kelimeKarsilik = view.findViewById(R.id.tvkelimeKarsilik)
            toplamKelime = view.findViewById(R.id.tvToplamKelime)
            kelimeSilButon = view.findViewById(R.id.btnKelimeSil)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kelimeKartiTasarimTutucu {


        if (kelimelerListesi.size < 1) {

            val tasarim =
                LayoutInflater.from(parent.context).inflate(R.layout.wordblank, parent, false)
            return kelimeKartiTasarimTutucu(tasarim)

        } else {

            val tasarim = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_liste_tasarim, parent, false)
            return kelimeKartiTasarimTutucu(tasarim)
        }
    }

    override fun onBindViewHolder(holder: kelimeKartiTasarimTutucu, position: Int) {

        val veri = kelimelerListesi.get(position)

        holder.anaKelime.text = veri.ana_kelime
        holder.kelimeKarsilik.text = veri.kelime_karsilik
        holder.toplamKelime?.text = kelimelerListesi.size.toString()

        holder.kelimeSilButon.setOnClickListener {

            val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_delete_kelime, null)
            val mBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(mDialogView1)
                .show()

            mBuilder.window?.setGravity(Gravity.BOTTOM)
            mBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialogView1.findViewById<TextView>(R.id.silinecekAnaKelime).text =veri.ana_kelime+" kelimesi silinecektir!"

            mDialogView1.btnKelimeSil.setOnClickListener {

                Log.e("Kelime Sil", "Çalıştı")
                ListelerimFragment().kelimeSil(veri.kelime_id)
                kelimelerListesi.clear()
                kelimelerListesi.addAll(java.util.ArrayList<Kelimeler>())
                notifyDataSetChanged()
                mBuilder.dismiss()
            }
        }


        holder.satirCardView.setOnClickListener {

            kelimeSecInterface.kelimeSec(veri.kelime_id)
            Log.e("Kelime ID:", veri.kelime_id.toString())

            val mDialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.kelimeler_dialog_fragment, null)
            val mBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(mDialogView).show()
            mBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mDialogView.findViewById<TextView>(R.id.dfAnaKelime).text = veri.ana_kelime
            mDialogView.findViewById<TextView>(R.id.dfKarsilik).text = veri.kelime_karsilik
            mDialogView.findViewById<TextView>(R.id.dfCumle).text = veri.cumle

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