package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.anasayfa_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ListelerimFragment : Fragment() {

    private lateinit var urunlerListe: ArrayList<Kelimeler>
    private lateinit var dillerListe:ArrayList<Diller>
    private var adapter1 = kelimelerAdapter(arrayListOf())
    private lateinit var adapter2 : dilAdapter
    private lateinit var kdi: KelimelerDaoInterface
    private lateinit var searchView: SearchView

    private var dilByKullaniciID : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflat1 = inflater.inflate(R.layout.anasayfa_layout, container, false)

        return inflat1

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter2 = dilAdapter(arrayListOf(), object : dilAdapter.OnItemClickListener {
            override fun OnItemClicked(id: Int) {

                kelimeListele(id)

                Bundle(id)

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

                    }

                }else{
                    val liste = response?.body()?.kelimeler
                    adapter1.update(liste as java.util.ArrayList<Kelimeler>)

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

    fun Bundle(id: Int){

        val Bundle = Bundle()
        Bundle.putInt("dilID", id)
        val yeniKelimeFragment = YeniKelimeEkle()
        yeniKelimeFragment.arguments = Bundle

        fabNew.setOnClickListener {

            Log.e("DİlByKullaniciID = ", ""+id)


            requireFragmentManager().beginTransaction().replace(R.id.fragmentTutucu, yeniKelimeFragment).addToBackStack(null).commit()

        }


    }









}