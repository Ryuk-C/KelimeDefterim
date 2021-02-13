package com.example.haznedar.kelimedefterim.Activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.yeni_kelime_ekle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YeniKelimeEkle: Fragment() {

    private lateinit var kdi: KelimelerDaoInterface
    private var adapter1 = kelimelerAdapter(arrayListOf())
    private lateinit var adapter2 : dilAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflat3 = inflater.inflate(R.layout.yeni_kelime_ekle, container, false)
        return inflat3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kdi = ApiUtils.getKelimelerDaoInterface()

        /*

            adapter2 = dilAdapter(arrayListOf(), object : dilAdapter.OnItemClickListener {
                override fun OnItemClicked(id: Int) {

                    kelimeEkle(id)
                    Log.e("Dilid",""+id)
                }
            }) */
    }
/*
    fun kelimeEkle(DilID : Int){
        fabKelimeEkle.setOnClickListener {

            Log.e("Dilid:", ""+DilID)

            val Pst_Kelime = etYeniKelime.text.toString().trim()
            val Pst_Karsilik = etYeniKelimeKarsilik.text.toString().trim()
            val Pst_Cumle = etYeniCumle.text.toString().trim()

            kdi.kelimeEkle(DilID, Pst_Kelime, Pst_Karsilik ,Pst_Cumle ).enqueue(object : Callback<CRUDCevap> {

                override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {

                }

                override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {

                    Log.e("Hata", "Hata 1")

                }
            })
        }
    }
*/
}