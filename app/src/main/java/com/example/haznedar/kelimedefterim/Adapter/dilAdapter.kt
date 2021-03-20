package com.example.haznedar.kelimedefterim.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Database.Diller
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.interfacee.DilSecInterface
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_dil_tasarim.*

class dilAdapter(
    private val dillerListesi: java.util.ArrayList<Diller>,
    private val dilSecInterface: DilSecInterface
) : RecyclerView.Adapter<dilAdapter.dilKartiTasarimTutucu>() {
     var selectedItemPosition: Int = 0


    inner class dilKartiTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView: CardView
        var dil: TextView
        var dilResim: ImageView
        var dilLayout : LinearLayout


        init {
            satirCardView = view.findViewById(R.id.cardViewDil)
            dil = view.findViewById(R.id.tvDil)
            dilResim = view.findViewById(R.id.ivdilBayrak)
            dilLayout = view.findViewById(R.id.LinearDil)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dilKartiTasarimTutucu {
        val tasarim =
            LayoutInflater.from(parent.context).inflate(R.layout.card_dil_tasarim, parent, false)
        return dilKartiTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: dilKartiTasarimTutucu, position: Int) {

        val veri = dillerListesi.get(position)

        holder.dil.text = veri.dil_isim

        val url = "http://haznedar.de/Haznedar/KelimeDefterim/Resimler/Bayraklar/${veri.dil_resim}"

        Picasso.get().load(url).into(holder.dilResim)


        holder.satirCardView.setOnClickListener {

            dilSecInterface.dilSec(veri.dil_id.toString())

            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()

        }

        if (selectedItemPosition == position){

            holder.dilLayout.setBackgroundColor(Color.parseColor("#216afc"))
            holder.dil.setTextColor(Color.parseColor("#FFFFFF"))


        }else{
            holder.dilLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.dil.setTextColor(Color.parseColor("#808080"))

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