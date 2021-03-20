package com.example.haznedar.kelimedefterim.Translate

import com.example.haznedar.kelimedefterim.Activity.TranslateFragment

class test {

    companion object{

        @JvmStatic
        fun main(args: Array<String>) {

            val translateRequest = Translate(TranslateFragment().spnSeciliGelenDil, TranslateFragment().spnSeciliHedefDil, TranslateFragment().SeciliMetin)
            val response = translateRequest.Post()
            println(Translate.prettify(response))

            print(response[0])


        }


    }
}