package com.example.haznedar.kelimedefterim.Testler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Chronometer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haznedar.kelimedefterim.Adapter.coktanSecmeliTestAdapter
import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.model.secmeliTestModel
import com.example.haznedar.szlk.ApiUtils
import kotlinx.android.synthetic.main.activity_coktan_secmeli.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoktanSecmeliActivity : AppCompatActivity() {

    private lateinit var kdi: KelimelerDaoInterface
    private var kullaniciDilID = "1"
    var coktanSecmeliTestAdapter = coktanSecmeliTestAdapter(arrayListOf())
    lateinit var kronometre : Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coktan_secmeli)


        kullaniciDilID = intent.getStringExtra("kullanıcıDilID").toString()
        Log.e("Gelen Dil ID", kullaniciDilID)
        kronometre = findViewById(R.id.tvSayac)

        rlCoktanSecmeliSorular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rlCoktanSecmeliSorular.adapter = coktanSecmeliTestAdapter

        testSorulariGetir(kullaniciDilID)
        kronometreBaslat()
        kronometreSonlandir()

        btnYenile.setOnClickListener {

            testSorulariGetir(kullaniciDilID)
        }

    }


    fun testSorulariGetir(id:String){

        kdi = ApiUtils.getKelimelerDaoInterface()

        kdi.coktanSecmeliTestSorusuGetir(id).enqueue(object : Callback<secmeliTestModel>{
            override fun onResponse(
                call: Call<secmeliTestModel>,
                response: Response<secmeliTestModel>
            ) {

                if (response != null) {

                    val liste = response.body()?.soruSiklarJSON

                    if (liste != null) {

                        Handler(Looper.getMainLooper()).postDelayed({
                            coktanSecmeliTestAdapter.update(liste as java.util.ArrayList<secmeliTestModel.SoruSiklarJSON>)
                            Log.e("gelen_soru", liste.toString())

                        }, 2000)


                    }
                }

                Log.e("Test Soruları", "çalıştı")

            }

            override fun onFailure(call: Call<secmeliTestModel>, t: Throwable) {

                Log.e("Test Soruları", "çalışmadı")

            }

        })
    }

    fun kronometreBaslat(){

        kronometre.start()
    }

    fun kronometreSonlandir(){

        tvTestiSonlandir.setOnClickListener {
            kronometre.stop()
        }

    }
}