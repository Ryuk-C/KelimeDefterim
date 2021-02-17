package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haznedar.kelimedefterim.Database.CRUDCevap
import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_giris.*
import kotlinx.android.synthetic.main.activity_kayit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GirisActivity : AppCompatActivity() {

    private lateinit var kdi: KelimelerDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giris)

        btnKayitOlSayfasi.setOnClickListener {
            val intent = Intent(this, KayitActivity::class.java)
            startActivity(intent)
        }


        btnUyeGiris.setOnClickListener {
            girisYap()
        }


    }

    fun girisYap() {

        val sp = getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)

        kdi = ApiUtils.getKelimelerDaoInterface()


        val Pst_EMail = etEmail1.text.toString().trim()
        val Pst_Sifre = etSifre1.text.toString().trim()


        kdi.kullaniciAra(Pst_EMail, Pst_Sifre).enqueue(object :
            Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {

                if (response.body()?.success == 1) {

                    val editor = sp.edit()
                    editor.putString("kullaniciAdi", Pst_EMail)
                    editor.putString("kullaniciSifre", Pst_Sifre)
                    editor.putString("kullanici_id", response.body()!!.kullaniciid)
                    editor.commit()


                    val intent = Intent(this@GirisActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    FancyToast.makeText(
                        this@GirisActivity, "Giriş İşlemi Başarılı!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS, false
                    ).show()
                }

                if (response.body()?.success == 0) {
                    FancyToast.makeText(
                        this@GirisActivity, "Giriş İşlemi Başarısız!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR, false
                    ).show()
                }
            }

            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {

            }
        })
    }
}