package com.example.haznedar.kelimedefterim.Adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.Testler.CoktanSecmeliActivity
import com.example.haznedar.kelimedefterim.model.secmeliTestModel
import kotlinx.android.synthetic.main.dialog_test_dogru_cevap.view.*
import kotlinx.android.synthetic.main.dialog_test_yanlis_cevap.view.*

class coktanSecmeliTestAdapter(private val coktanSecmeliSorular: java.util.ArrayList<secmeliTestModel.SoruSiklarJSON>, ) :
    RecyclerView.Adapter<coktanSecmeliTestAdapter.coktanSecmeliTestTasarimTutucu>() {

    inner class coktanSecmeliTestTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {

        var satirCardView1: CardView
        var satirCardView2: CardView
        var satirCardView3: CardView
        var satirCardView4: CardView
        var tvSoruAnaKelime: TextView
        var tvSoruCevabi1: TextView
        var tvSoruCevabi2: TextView
        var tvSoruCevabi3: TextView
        var tvSoruCevabi4: TextView

        init {
            satirCardView1 = view.findViewById(R.id.cvCoktanSecmeliSorular1)
            satirCardView2 = view.findViewById(R.id.cvCoktanSecmeliSorular2)
            satirCardView3 = view.findViewById(R.id.cvCoktanSecmeliSorular3)
            satirCardView4 = view.findViewById(R.id.cvCoktanSecmeliSorular4)
            tvSoruAnaKelime = view.findViewById(R.id.tvSoruAnaKelime)
            tvSoruCevabi1 = view.findViewById(R.id.tvSoruCevabi1)
            tvSoruCevabi2 = view.findViewById(R.id.tvSoruCevabi2)
            tvSoruCevabi3 = view.findViewById(R.id.tvSoruCevabi3)
            tvSoruCevabi4 = view.findViewById(R.id.tvSoruCevabi4)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): coktanSecmeliTestTasarimTutucu {
        val tasarim = LayoutInflater.from(parent.context).inflate(R.layout.liste_coktan_secmeli_sorular, parent, false)
        return coktanSecmeliTestTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: coktanSecmeliTestTasarimTutucu, position: Int) {

        val veri = coktanSecmeliSorular.get(position)
        val kullaniciByDilID = veri.Pst_KullaniciDilID

        //holder.dil.text = veri.dil_isim
        holder.tvSoruAnaKelime.text = veri.SecilenKelime
        holder.tvSoruCevabi1.text = veri.sik1
        holder.tvSoruCevabi2.text = veri.sik2
        holder.tvSoruCevabi3.text = veri.sik3
        holder.tvSoruCevabi4.text = veri.sik4

        val cardColor = ContextCompat.getColor(holder.itemView.context, R.color.green)

        holder.satirCardView1.setOnClickListener {


            if (veri.dY1 == "1") {

                holder.satirCardView1.setCardBackgroundColor(cardColor)
                holder.tvSoruCevabi1.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_dogru_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt.setOnClickListener {


                    mBuilder1.dismiss()
                    //notifyDataSetChanged()


                }

                mBuilder1.setOnDismissListener {
                    Log.e("gelen_soru",kullaniciByDilID)
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                }

            } else {

                if (veri.dY2 == "1") {

                    holder.satirCardView2.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi2.setTextColor(Color.WHITE)

                } else if (veri.dY3 == "1") {
                    holder.satirCardView3.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi3.setTextColor(Color.WHITE)
                } else if (veri.dY4 == "1") {
                    holder.satirCardView4.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi4.setTextColor(Color.WHITE)
                }

                holder.satirCardView1.setCardBackgroundColor(Color.RED)
                holder.tvSoruCevabi1.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_yanlis_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt1.setOnClickListener {
                    Log.e("gelen_soru",kullaniciByDilID)
                    //notifyDataSetChanged()
                    mBuilder1.dismiss()

                }

                mBuilder1.setOnDismissListener {
                    Log.e("gelen_soru",kullaniciByDilID)
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                }
            }
        }

        holder.satirCardView2.setOnClickListener {


            if (veri.dY2 == "1") {

                holder.satirCardView2.setCardBackgroundColor(cardColor)
                holder.tvSoruCevabi2.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_dogru_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                    //notifyItemChanged(position)
                    mBuilder1.dismiss()
                }


            } else {

                if (veri.dY1 == "1") {

                    holder.satirCardView1.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi1.setTextColor(Color.WHITE)

                } else if (veri.dY3 == "1") {
                    holder.satirCardView3.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi3.setTextColor(Color.WHITE)
                } else if (veri.dY4 == "1") {
                    holder.satirCardView4.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi4.setTextColor(Color.WHITE)
                }

                holder.satirCardView2.setCardBackgroundColor(Color.RED)
                holder.tvSoruCevabi2.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_yanlis_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt1.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                   // notifyItemChanged(position)
                    mBuilder1.dismiss()

                }

            }


        }

        holder.satirCardView3.setOnClickListener {

            if (veri.dY3 == "1") {

                holder.satirCardView3.setCardBackgroundColor(cardColor)
                holder.tvSoruCevabi3.setTextColor(Color.WHITE)


                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_dogru_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                   // notifyItemChanged(position)
                    mBuilder1.dismiss()
                }

            } else {

                if (veri.dY1 == "1") {

                    holder.satirCardView1.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi1.setTextColor(Color.WHITE)

                } else if (veri.dY2 == "1") {
                    holder.satirCardView2.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi2.setTextColor(Color.WHITE)
                } else if (veri.dY4 == "1") {
                    holder.satirCardView4.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi4.setTextColor(Color.WHITE)
                }

                holder.satirCardView3.setCardBackgroundColor(Color.RED)
                holder.tvSoruCevabi3.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_yanlis_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt1.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                   // notifyItemChanged(position)
                    mBuilder1.dismiss()

                }
            }

        }

        holder.satirCardView4.setOnClickListener {

            if (veri.dY4 == "1") {

                holder.satirCardView4.setCardBackgroundColor(cardColor)
                holder.tvSoruCevabi4.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_dogru_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                    //notifyItemChanged(position)
                    mBuilder1.dismiss()

                }
            } else {

                if (veri.dY1 == "1") {

                    holder.satirCardView1.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi1.setTextColor(Color.WHITE)

                } else if (veri.dY2 == "1") {
                    holder.satirCardView2.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi2.setTextColor(Color.WHITE)
                } else if (veri.dY3 == "1") {
                    holder.satirCardView3.setCardBackgroundColor(cardColor)
                    holder.tvSoruCevabi3.setTextColor(Color.WHITE)
                }

                holder.satirCardView4.setCardBackgroundColor(Color.RED)
                holder.tvSoruCevabi4.setTextColor(Color.WHITE)

                val mDialogView1 = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_test_yanlis_cevap, null)
                val mBuilder1 = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView1)
                    .setCancelable(false)
                    .show()

                mBuilder1.window?.setGravity(Gravity.BOTTOM)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mDialogView1.btnDevamEt1.setOnClickListener {

                    Log.e("btnDevamEt", "Çalıştı")
                    CoktanSecmeliActivity().testSorulariGetir(kullaniciByDilID)
                    Log.e("Adaper Dil ID", kullaniciByDilID)
                    //notifyDataSetChanged()
                    //notifyItemChanged(position)
                    mBuilder1.dismiss()

                }
            }
        }


/*
        if (selectedItemPosition == position){

            holder.dilLayout.setBackgroundColor(Color.parseColor("#216afc"))
            holder.dil.setTextColor(Color.parseColor("#FFFFFF"))


        }else{
            holder.dilLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.dil.setTextColor(Color.parseColor("#808080"))
        }
*/

    }

    override fun getItemCount(): Int {
        return coktanSecmeliSorular.size
    }

    fun update(newList: java.util.ArrayList<secmeliTestModel.SoruSiklarJSON>) {
        coktanSecmeliSorular.clear()
        coktanSecmeliSorular.addAll(newList)
        Log.e("gelen_soru_update",newList.toString())
        notifyDataSetChanged()
        Log.e("gelen_soru_update",newList.size.toString())

    }

}