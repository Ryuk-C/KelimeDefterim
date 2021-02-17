package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.haznedar.kelimedefterim.Adapter.akademiDilAdapter
import com.example.haznedar.kelimedefterim.Database.Diller
import com.example.haznedar.kelimedefterim.Database.DillerCevap
import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.akademi_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AkademiFragment : Fragment() {

    private lateinit var adapter3: akademiDilAdapter
    private lateinit var kdi: KelimelerDaoInterface
    private var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflat3 = inflater.inflate(R.layout.akademi_layout, container, false)
        return inflat3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter3 = akademiDilAdapter(arrayListOf(), object : akademiDilAdapter.OnItemClickListener {
            override fun OnItemClicked(id: Int) {

            }
        })

        kdi = ApiUtils.getKelimelerDaoInterface()
        rvAkademiDilTutucu.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rvAkademiDilTutucu.adapter = adapter3

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvAkademiDilTutucu)


        dilListele()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
            override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
                val view = super.findSnapView(layoutManager)
                if (view != null) {
                    val newPosition = layoutManager.getPosition(view)
                    if (newPosition != selectedPosition && rvAkademiDilTutucu.scrollState === RecyclerView.SCROLL_STATE_IDLE) {
                        onViewSnapped(newPosition)
                        selectedPosition = newPosition
                    }
                }
                return view
            }
        }
    }

    private fun onViewSnapped(index: Int) {

    }

    fun dilListele() {

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)
        val ogkid = sp?.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()



        kdi.tumDiller(ogkid).enqueue(object : Callback<DillerCevap> {

            override fun onResponse(call: Call<DillerCevap>?, response: Response<DillerCevap>?) {

                if (response != null) {

                    val liste = response.body()?.diller


                    if (liste != null) {

                        adapter3.update(liste as java.util.ArrayList<Diller>)

                    }
                }
            }

            override fun onFailure(call: Call<DillerCevap>?, t: Throwable?) {

            }
        })
    }

}