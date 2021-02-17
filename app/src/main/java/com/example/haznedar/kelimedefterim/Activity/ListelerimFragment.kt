package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter2
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.interfacee.DilSecInterface
import com.example.haznedar.kelimedefterim.interfacee.KelimeSecInterface
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.anasayfa_layout.*
import kotlinx.android.synthetic.main.anasayfa_layout.view.*
import kotlinx.android.synthetic.main.card_liste_tasarim.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListelerimFragment : Fragment(), SearchView.OnQueryTextListener, DilSecInterface,
    KelimeSecInterface {

    private lateinit var urunlerListe: ArrayList<Kelimeler>
    private lateinit var dillerListe: ArrayList<Diller>
    var kelimeAdapterYeni = kelimelerAdapter(arrayListOf(), this@ListelerimFragment as KelimeSecInterface)
    var dilAdapterYeni = dilAdapter2(arrayListOf(), this@ListelerimFragment as DilSecInterface)
    private lateinit var kdi: KelimelerDaoInterface
    private var kullaniciByDilID = ""
    private var kelimeByID = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val inflat1 = inflater.inflate(R.layout.anasayfa_layout, container, false)
        (activity as AppCompatActivity).setSupportActionBar(inflat1.toolbar)
        return inflat1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDil.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rvDil.adapter = dilAdapterYeni

        rvKelimeler.layoutManager = LinearLayoutManager(this.context)
        rvKelimeler.adapter = kelimeAdapterYeni

        kdi = ApiUtils.getKelimelerDaoInterface()

        dilListele()

    }

    fun kelimeListele(id: String, user_ID:Int) {

        kdi.tumKelimeler(id.toInt(), user_ID).enqueue(object : Callback<KelimelerCevap> {

            override fun onResponse(
                call: Call<KelimelerCevap>?,
                response: Response<KelimelerCevap>?
            ) {

                if (response != null) {

                    val liste = response.body()?.kelimeler

                    if (liste != null) {

                        kelimeAdapterYeni.update(liste as java.util.ArrayList<Kelimeler>)

                    }

                } else {
                    val liste = response?.body()?.kelimeler
                    kelimeAdapterYeni.update(liste as java.util.ArrayList<Kelimeler>)

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

                    }
                }
            }

            override fun onFailure(call: Call<DillerCevap>?, t: Throwable?) {

            }
        })
    }

    fun kelimeSil(kelimeID: Int) {

        kdi = ApiUtils.getKelimelerDaoInterface()

        kdi.kelimeSil(kelimeID).enqueue(object : Callback<CRUDCevap> {

            override fun onResponse(
                call: Call<CRUDCevap>?,
                response: Response<CRUDCevap>?
            ) {

            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {

            }
        })
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

                        kelimeAdapterYeni.update(liste as java.util.ArrayList<Kelimeler>)
                    }
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ara -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()

        kullaniciByDilID = dilID
        kelimeListele(dilID,ogkid.toInt())
        Bundle(dilID.toInt())
    }

    override fun kelimeSec(kelimeID: Int) {

        kelimeByID = kelimeID
    }
}