package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.haznedar.kelimedefterim.Database.CRUDCevap
import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    private lateinit var kdi: KelimelerDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        object : CountDownTimer(1600, 500) {
            override fun onFinish() {
                progressBar2.visibility= View.VISIBLE

                val sp = getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)

                kdi = ApiUtils.getKelimelerDaoInterface()

                val ogka = sp.getString("kullaniciAdi", "kullanici adi bulunmamaktadir").toString()
                val ogks =
                    sp.getString("kullaniciSifre", "kullanici sifre bulunmamaktadir").toString()
                val ogkid = sp.getString("kullanici_id", "kullanici id bulunmamaktadir.").toString()

                kdi.kullaniciAra(ogka, ogks).enqueue(object :
                    Callback<CRUDCevap> {
                    override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {

                        if (response.body()?.success == 1) {

                            Log.e("Kullanıcı", response.body()!!.kullaniciid)

                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                            FancyToast.makeText(
                                this@SplashActivity, "Hoşgeldiniz",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS, false
                            ).show()
                        }

                        if (response.body()?.success == 0) {
                            val intent = Intent(this@SplashActivity, GirisActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                        progressBar2.visibility= View.INVISIBLE
                    }
                })

            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
        super.onResume()
    }
}