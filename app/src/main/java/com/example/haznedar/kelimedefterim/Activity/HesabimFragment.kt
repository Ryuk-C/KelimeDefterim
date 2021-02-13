package com.example.haznedar.kelimedefterim.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.haznedar.kelimedefterim.R
import kotlinx.android.synthetic.main.hesabim_layout.*

class HesabimFragment:Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflat2 = inflater.inflate(R.layout.hesabim_layout, container, false)

        return inflat2

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCikisYap()


    }

    fun btnCikisYap(){

        val sp = this.activity?.getSharedPreferences("GirisBilgi", Context.MODE_PRIVATE)


        btnCikisYap.setOnClickListener {

            val intent = Intent(this.context, GirisActivity::class.java)
            startActivity(intent)
            val editor = sp?.edit()
            editor?.remove("kullaniciAdi")
            editor?.remove("kullaniciSifre")
            editor?.commit()


        }



    }


}