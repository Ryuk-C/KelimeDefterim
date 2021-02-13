package com.example.haznedar.kelimedefterim.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haznedar.kelimedefterim.Database.CRUDCevap
import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.szlk.ApiUtils
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_kayit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KayitActivity : AppCompatActivity() {

    private lateinit var kdi: KelimelerDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit)

        btnGirisSayfasi.setOnClickListener {
            val intent = Intent(this, GirisActivity::class.java)
            startActivity(intent)
        }

        btnKayitOlbtn.setOnClickListener {
            uyelikBaslat()
        }

    }



    fun uyelikBaslat() {

        kdi = ApiUtils.getKelimelerDaoInterface()

        val Pst_EMail = etuyelikmail.text.toString().trim()
        val Pst_Sifre = etuyeliksifre.text.toString().trim()


        if (Pst_EMail.trim().length > 0 && Pst_Sifre.trim().length > 0) {

            if (etuyeliksifre.text.toString() != etuyeliksifre2.text.toString()) {

                FancyToast.makeText(
                        this@KayitActivity,
                        "Şifreler Uyuşmamaktadır!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        true
                ).show()
            } else {
                kdi.kullaniciEkle(Pst_EMail, Pst_Sifre).enqueue(object :
                        Callback<CRUDCevap> {
                    override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {

                    }

                    override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                    }
                })

                FancyToast.makeText(
                        this@KayitActivity, "Üyelik İşlemi Başarılı!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS, true
                ).show()

                val intent = Intent(this@KayitActivity, GirisActivity::class.java)
                startActivity(intent)
            }
        } else {
            FancyToast.makeText(
                    this@KayitActivity, "Lütfen boş değer bırakmayınız!",
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, false
            ).show()
        }
    }
}