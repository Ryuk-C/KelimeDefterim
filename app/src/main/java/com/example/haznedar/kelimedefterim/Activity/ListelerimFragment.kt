package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Adapter.dilAdapter
import com.example.haznedar.kelimedefterim.Adapter.eklenmemisDilAdapter
import com.example.haznedar.kelimedefterim.Adapter.kelimelerAdapter
import com.example.haznedar.kelimedefterim.Database.*
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.anasayfa_layout.*
import kotlinx.android.synthetic.main.anasayfa_layout.view.*
import kotlinx.android.synthetic.main.card_dil_tasarim.*
import kotlinx.android.synthetic.main.card_liste_tasarim.*
import kotlinx.android.synthetic.main.dialog_yeni_dil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListelerimFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var urunlerListe: ArrayList<Kelimeler>
    private lateinit var dillerListe: ArrayList<Diller>
    lateinit var veriAdapter: ArrayAdapter<String>
    private var recycler_view: RecyclerView? = null

    var kelimeAdapterYeni = kelimelerAdapter(arrayListOf())
    var dilAdapterYeni = dilAdapter(arrayListOf())
    var eklenmemisDilAdapterYeni = eklenmemisDilAdapter(arrayListOf())
    private lateinit var kdi: KelimelerDaoInterface
    var kullaniciByDilID = ""
    private var kelimeByID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflat1 = inflater.inflate(R.layout.anasayfa_layout, container, false)
        (activity as AppCompatActivity).setSupportActionBar(inflat1.toolbar)
        return inflat1

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDil.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rvDil.adapter = dilAdapterYeni

        rvKelimeler.layoutManager = LinearLayoutManager(this.context)
        rvKelimeler.adapter = kelimeAdapterYeni

        kdi = ApiUtils.getKelimelerDaoInterface()


        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "").toString()


        kelimeAdapterYeni.kelimeSecYeni = { secilenKelimeId ->
            Log.d("secilenkelimeid", secilenKelimeId)
            kelimeByID = secilenKelimeId.toInt()
        }

        dilAdapterYeni.dilSecYeni = { secilenDilId ->

            kullaniciByDilID = secilenDilId
            Bundle(kullaniciByDilID.toInt())
            kelimeListele(kullaniciByDilID, ogkid)

        }


        dilListele()
        kelimeListele(kullaniciByDilID, ogkid)

    }

    fun kelimeListele(id: String, user_ID: String) {

        kdi = ApiUtils.getKelimelerDaoInterface()


        kdi.tumKelimeler(id, user_ID).enqueue(object : Callback<KelimelerCevap> {

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

    fun eklenmemisDilListele() {

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "").toString()
        kdi = ApiUtils.getKelimelerDaoInterface()

        kdi.eklenmemisDilListele(ogkid).enqueue(object : Callback<DillerCevap> {

            override fun onResponse(call: Call<DillerCevap>?, response: Response<DillerCevap>?) {

                if (response != null) {

                    val liste = response.body()?.diller


                    if (liste != null) {

                        eklenmemisDilAdapterYeni.update(liste as java.util.ArrayList<Diller>)

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

    fun kelimeGuncelle(kelimeID: Int, kelime: String, kelimeKarsilik: String, ornekCumle: String?) {

        kdi = ApiUtils.getKelimelerDaoInterface()

        kdi.kelimeGuncelle(kelimeID, kelime, kelimeKarsilik, ornekCumle)
            .enqueue(object : Callback<CRUDCevap> {

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

            R.id.yeni_liste -> {

                val mDialogView1 = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_yeni_dil, null)
                val mBuilder1 = AlertDialog.Builder(requireContext())
                    .setView(mDialogView1)
                    .show()

                val rvEklenmemisDiller =
                    mDialogView1.findViewById<RecyclerView>(R.id.rvEklenmemisDiller)
                rvEklenmemisDiller.layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                rvEklenmemisDiller.adapter = eklenmemisDilAdapterYeni

                mBuilder1.window?.setGravity(Gravity.CENTER)
                mBuilder1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                eklenmemisDilListele()

                true
            }

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


}
