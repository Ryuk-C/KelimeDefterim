package com.example.haznedar.kelimedefterim.Activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.anasayfa_layout.*
import kotlinx.android.synthetic.main.yeni_kelime_ekle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListelerimFragment : Fragment() {

    private lateinit var urunlerListe: ArrayList<Kelimeler>
    private lateinit var dillerListe:ArrayList<Diller>
    private var adapter1 = kelimelerAdapter(arrayListOf())
    private lateinit var adapter2 : dilAdapter
    private lateinit var kdi: KelimelerDaoInterface
    private lateinit var searchView: SearchView

      var dilByKullaniciID : Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflat1 = inflater.inflate(R.layout.anasayfa_layout, container, false)

        return inflat1

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter2 = dilAdapter(arrayListOf(), object : dilAdapter.OnItemClickListener {
            override fun OnItemClicked(id: Int) {

                kelimeListele(id)

                dilByKullaniciID = id

            }

        })

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)

        val ogkid = sp?.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()

        rvDil.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rvDil.adapter = adapter2

        rvKelimeler.layoutManager = LinearLayoutManager(this.context)
        rvKelimeler.adapter = adapter1




        kdi = ApiUtils.getKelimelerDaoInterface()

        dilListele()

        /*

        fabNew.setOnClickListener {

            val tasarim = layoutInflater.inflate(R.layout.yeni_kelime_ekle, null)
            val Pst_Kelime = tasarim.findViewById(R.id.etYeniKelime) as EditText
            val Pst_Karsilik = tasarim.findViewById(R.id.etYeniKelimeKarsilik) as EditText
            val Pst_Cumle = tasarim.findViewById(R.id.etYeniCumle) as EditText

            val Pst_Kelime1 = etYeniKelime.text.toString().trim()
            val Pst_Karsilik1 = etYeniKelimeKarsilik.text.toString().trim()
            val Pst_Cumle1 = etYeniCumle.text.toString().trim()

            val ad = AlertDialog.Builder(this.context)
            ad.setTitle("Yeni Kelime Oluştur")

            ad.setView(tasarim)

            ad.setPositiveButton("Oluştur"){ dialogInterface, i ->

                kdi.kelimeEkle(2, Pst_Kelime1, Pst_Karsilik1 ,Pst_Cumle1 ).enqueue(object : Callback<CRUDCevap> {

                    override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {

                    }

                    override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {

                        Log.e("Hata", "Hata 1")

                    }
                })


            }

            ad.setNegativeButton("İptal"){ dialogInterface, i ->


            }

            ad.create().show()
        }

        */

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_ara)
        searchView = searchItem.actionView as SearchView

    }

    fun kelimeListele(id: Int){

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)


        kdi.tumKelimeler(id).enqueue(object : Callback<KelimelerCevap> {

            override fun onResponse(call: Call<KelimelerCevap>?, response: Response<KelimelerCevap>?) {

                if (response != null) {

                    val liste = response.body()?.kelimeler

                    if (liste != null) {

                        adapter1.update(liste as java.util.ArrayList<Kelimeler>)

                    }else{

                    }
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
    }

    fun dilListele(){

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()



        kdi.tumDiller(ogkid).enqueue(object : Callback<DillerCevap> {

            override fun onResponse(call: Call<DillerCevap>?, response: Response<DillerCevap>?) {

                if (response != null) {

                    val liste = response.body()?.diller


                    if (liste != null) {

                        adapter2.update(liste as java.util.ArrayList<Diller>)

                    }

                }


            }

            override fun onFailure(call: Call<DillerCevap>?, t: Throwable?) {

            }
        })
    }


/*
    fun aramaYap(aramaKelime:String){

        kdi.kelimeAra(aramaKelime).enqueue(object : Callback<KelimelerCevap>{

            override fun onResponse(call: Call<KelimelerCevap>?, response: Response<KelimelerCevap>?) {

                if(response != null) {

                    val liste = response.body()?.kelimeler

                    if (liste != null) {

                        adapter1.update(liste as java.util.ArrayList<Kelimeler>)
                    }
                }
            }
            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })

    } */



}