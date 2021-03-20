package com.example.haznedar.kelimedefterim.Activity

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.haznedar.kelimedefterim.R
import com.example.haznedar.kelimedefterim.Translate.Translate
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Protocol
import kotlinx.android.synthetic.main.fragment_translate.*
import java.util.*


class TranslateFragment : Fragment() {

    val typesGelenDil = arrayOf(
        "tr",
        "en",
        "de",
        "es",
        "fr",
        "pt",
        "ko",
        "hi",
        "it",
        "ru",
        "ja",
        "ar",
        "zh-Hans",
        "nb",
        "sv",
        "pl"
    )
    val typesHedefDil = arrayOf(
        "en",
        "tr",
        "de",
        "es",
        "fr",
        "pt",
        "ko",
        "hi",
        "it",
        "ru",
        "ja",
        "ar",
        "zh-Hans",
        "nb",
        "sv",
        "pl"
    )
    var spnSeciliGelenDil = "tr"
    var spnSeciliHedefDil = "en"
    var SeciliMetin = "Seni seviyorum"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val t = inflater.inflate(R.layout.fragment_translate, container, false)
        val spnGelenDil = t.findViewById<Spinner>(R.id.spnGelenDil)
        val spnHedefDil = t.findViewById<Spinner>(R.id.spnHedefDil)

        spnGelenDil?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            typesGelenDil
        ) as SpinnerAdapter
        spnGelenDil?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity, type, Toast.LENGTH_LONG).show()
                println(type)
                spnSeciliGelenDil = type
            }
        }

        spnHedefDil?.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            typesHedefDil
        ) as SpinnerAdapter
        spnHedefDil?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity, type, Toast.LENGTH_LONG).show()
                println(type)
                spnSeciliHedefDil = type
            }

        }

        return t
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnKelimeyiCevir.setOnClickListener {
            Log.e("ceviri", "tıklandı")
            translate()
        }

    }

/*
    fun translate(){

        Log.e("GelenDil", ""+ spnSeciliGelenDil)
        Log.e("HedefDil", ""+ spnSeciliHedefDil)


        thread {

            Runnable{


                try {
                    val translateRequest = Translate(
                        spnSeciliGelenDil,
                        spnSeciliHedefDil,
                        SeciliMetin
                    )
                    val response = translateRequest.Post()
                    println(Translate.prettify(response))
                } catch (e: Exception) {

                    Log.e("Translate", e.toString())
                    tvCevrilmisMetin.text = e.toString()
                }


            }

        }.start()


            }


 */


    fun translate() {


        Log.e("GelenDil", "" + spnSeciliGelenDil)
        Log.e("HedefDil", "" + spnSeciliHedefDil)

        Thread {
            try {
                val translateRequest = Translate(
                    spnSeciliGelenDil,
                    spnSeciliHedefDil,
                    SeciliMetin
                )
                val response = translateRequest.Post()
                println(Translate.prettify(response))
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                Log.e("Translate", ex.toString())
            }
        }.start()


    }


}



