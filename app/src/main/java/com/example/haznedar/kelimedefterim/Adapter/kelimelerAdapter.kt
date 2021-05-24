package com.example.haznedar.kelimedefterim.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Activity.ListelerimFragment
import com.example.haznedar.kelimedefterim.Database.Kelimeler
import com.example.haznedar.kelimedefterim.R
import kotlinx.android.synthetic.main.dialog_delete_kelime.view.*
import kotlinx.android.synthetic.main.kelimeler_dialog_fragment.view.*
import kotlinx.android.synthetic.main.update_kelimeler.view.*

class kelimelerAdapter(

    private val kelimelerListesi: java.util.ArrayList<Kelimeler>,

) :

    RecyclerView.Adapter<kelimelerAdapter.kelimeKartiTasarimTutucu>() {

    var kelimeSecYeni : (String) -> Unit = {_ ->}

    inner class kelimeKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var anaKelime: TextView
        var kelimeKarsilik: TextView
        var toplamKelime: TextView?

        init {
            satirCardView = view.findViewById(R.id.cardViewListe)
            anaKelime = view.findViewById(R.id.tvAnaKelime)
            kelimeKarsilik = view.findViewById(R.id.tvkelimeKarsilik)
            toplamKelime = view.findViewById(R.id.tvToplamKelime)
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


        holder.satirCardView.setOnClickListener {

            kelimeSecYeni.invoke(veri.kelime_id.toString())

            Log.e("Kelime ID:", veri.kelime_id.toString())

            val sp = holder.itemView.context.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
            val ogkid = sp?.getString("kullanici_id", "").toString()

            val mDialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.kelimeler_dialog_fragment, null)
            val mBuilder0 = AlertDialog.Builder(holder.itemView.context)
                .setView(mDialogView).show()
            mBuilder0.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            mDialogView.findViewById<TextView>(R.id.dfAnaKelime).text = veri.ana_kelime
            mDialogView.findViewById<TextView>(R.id.dfKarsilik).text = veri.kelime_karsilik
            mDialogView.findViewById<TextView>(R.id.dfCumle).text = veri.cumle

            mDialogView.btnKaldir.setOnClickListener {

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_delete_kelime, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialogView1.findViewById<TextView>(R.id.silinecekAnaKelime).text =
                    veri.ana_kelime + " kelimesi silinecektir!"

                mDialogView1.btnKelimeSil.setOnClickListener {

                    mBuilder0.dismiss()
                    Log.e("Kelime Sil", "Çalıştı")
                    ListelerimFragment().kelimeSil(veri.kelime_id)
                    kelimelerListesi.removeAt(position)
                    notifyDataSetChanged()
                    notifyItemChanged(position)
                    mBuilder1.dismiss()
                }
            }

            mDialogView.btnChange.setOnClickListener {
                val Layout = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.update_kelimeler, null)
                val chgAnaKelime = Layout.findViewById(R.id.upAnaKelime) as EditText
                val chgKarsilik = Layout.findViewById(R.id.upKarsilik) as EditText
                val chgCumle = Layout.findViewById(R.id.upCumle) as EditText

                chgAnaKelime.setText(veri.ana_kelime)
                chgKarsilik.setText(veri.kelime_karsilik)
                chgCumle.setText(veri.cumle)

                val mBuilder3 = AlertDialog.Builder(holder.itemView.context)
                    .setView(Layout).show()
                mBuilder3.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                Layout.btnKelimeUpdate.setOnClickListener {

                    val chgAnakelime1 = chgAnaKelime.text.toString().trim()
                    val chgKarsilik1 = chgKarsilik.text.toString().trim()
                    val chgCumle1 = chgCumle.text.toString().trim()

                    mBuilder0.dismiss()
                    ListelerimFragment().kelimeGuncelle(
                        veri.kelime_id,
                        chgAnakelime1,
                        chgKarsilik1,
                        chgCumle1
                    )
                    kelimelerListesi.clear()
                    ListelerimFragment().kelimeListele("", ogkid)
                    kelimelerListesi.addAll(java.util.ArrayList<Kelimeler>())
                    notifyDataSetChanged()
                    mBuilder3.dismiss()

                }
            }
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