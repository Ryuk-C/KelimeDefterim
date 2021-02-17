package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter2
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.interfacee.DilSecInterface
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.anasayfa_layout.*
import kotlinx.android.synthetic.main.anasayfa_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListelerimFragment : Fragment(), SearchView.OnQueryTextListener, DilSecInterface {

    private lateinit var urunlerListe: ArrayList<Kelimeler>
    private lateinit var dillerListe: ArrayList<Diller>
    private var kelimelerAdapter = kelimelerAdapter(arrayListOf())
    private lateinit var adapter2: dilAdapter

    var dilAdapterYeni =  dilAdapter2(arrayListOf(),this@ListelerimFragment as DilSecInterface)

    private lateinit var kdi: KelimelerDaoInterface
    private var selectedPosition = -1
    private var kullaniciByDilID = ""
   // private var kullaniciByDilID by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val inflat1 = inflater.inflate(R.layout.anasayfa_layout, container, false)
        (activity as AppCompatActivity).setSupportActionBar(inflat1.toolbar)

        return inflat1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /*
        adapter2 = dilAdapter(arrayListOf(), object : dilAdapter.OnItemClickListener {
            override fun OnItemClicked(id: Int) {

                kullaniciByDilID = id
                Bundle(id)
                kelimeListele(id)
            }
        })*/

        rvDil.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rvDil.adapter = dilAdapterYeni

        rvKelimeler.layoutManager = LinearLayoutManager(this.context)
        rvKelimeler.adapter = kelimelerAdapter

        kdi = ApiUtils.getKelimelerDaoInterface()

        dilListele()

    }

    fun kelimeListele(id: String) {

        kdi.tumKelimeler(id.toInt()).enqueue(object : Callback<KelimelerCevap> {

            override fun onResponse(
                call: Call<KelimelerCevap>?,
                response: Response<KelimelerCevap>?
            ) {

                if (response != null) {

                    val liste = response.body()?.kelimeler

                    if (liste != null) {

                        kelimelerAdapter.update(liste as java.util.ArrayList<Kelimeler>)

                    }

                } else {
                    val liste = response?.body()?.kelimeler
                    kelimelerAdapter.update(liste as java.util.ArrayList<Kelimeler>)

                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
    }

    fun dilListele() {

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()


        kdi.tumDiller(ogkid).enqueue(object : Callback<DillerCevap> {

            override fun onResponse(call: Call<DillerCevap>?, response: Response<DillerCevap>?) {

                if (response != null) {

                    val liste = response.body()?.diller


                    if (liste != null) {

                        dilAdapterYeni.update(liste as java.util.ArrayList<Diller>)
                       // adapter2.update(liste as java.util.ArrayList<Diller>)

                    }
                }
            }

            override fun onFailure(call: Call<DillerCevap>?, t: Throwable?) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.action_ara)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    fun Bundle(id: Int) {

        val Bundle = Bundle()
        Bundle.putInt("dilID", id)
        val yeniKelimeFragment = YeniKelimeEkle()
        yeniKelimeFragment.arguments = Bundle

        fabNew.setOnClickListener {

            requireFragmentManager().beginTransaction()
                .replace(R.id.fragmentTutucu, yeniKelimeFragment).addToBackStack(null).commit()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ara -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun aramaYap(arananDeger: String, kullaniciDILID: Int) {

        kdi.kelimeAra(arananDeger, kullaniciDILID).enqueue(object : Callback<KelimelerCevap> {

            override fun onResponse(
                call: Call<KelimelerCevap>?,
                response: Response<KelimelerCevap>?
            ) {

                if (response != null) {

                    val liste = response.body()?.kelimeler

                    if (liste != null) {

                        kelimelerAdapter.update(liste as java.util.ArrayList<Kelimeler>)
                    }
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.e("onQueryTextSubmit", query)

        aramaYap(query, kullaniciByDilID.toInt())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            Log.e("onQueryTextChange", newText)
            aramaYap(newText, kullaniciByDilID.toInt())
        }
        return true
    }

    override fun dilSec(dilID: String) {

        kullaniciByDilID = dilID
        kelimeListele(dilID)
        Bundle(dilID.toInt())
    }
}