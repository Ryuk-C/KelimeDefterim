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
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.yeni_kelime_ekle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class YeniKelimeEkle : Fragment() {

    private lateinit var kdi: KelimelerDaoInterface
    var inputText: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflat3 = inflater.inflate(R.layout.yeni_kelime_ekle, container, false)
        inputText = arguments?.getInt("dilID")!!

        return inflat3
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kdi = ApiUtils.getKelimelerDaoInterface()
        kelimeEkle()


    }

    fun kelimeEkle() {
        fabKelimeEkle.setOnClickListener {


            val Pst_Kelime = etYeniKelime.text.toString().trim()
            val Pst_Karsilik = etYeniKelimeKarsilik.text.toString().trim()
            val Pst_Cumle = etYeniCumle.text.toString().trim()

            if (Pst_Kelime.trim().length > 0 && Pst_Karsilik.trim().length > 0) {

                kdi.kelimeEkle(inputText, Pst_Kelime, Pst_Karsilik, Pst_Cumle)
                    .enqueue(object : Callback<CRUDCevap> {

                        override fun onResponse(
                            call: Call<CRUDCevap>?,
                            response: Response<CRUDCevap>?
                        ) {
                            if (response != null) {
                                if (response.body()?.success == 0) {

                                    FancyToast.makeText(
                                        requireContext(), response.body()!!.message,
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.WARNING, false
                                    ).show()

                                } else {
                                    requireFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentTutucu, ListelerimFragment())
                                        .addToBackStack(null).commit()

                                    FancyToast.makeText(
                                        requireContext(), "Yeni Kelime Eklendi!",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, false
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {


                        }
                    })

            } else {

                FancyToast.makeText(
                    requireContext(), "Değerleri boş bırakmayınız.",
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, false
                ).show()

            }

        }
    }

}