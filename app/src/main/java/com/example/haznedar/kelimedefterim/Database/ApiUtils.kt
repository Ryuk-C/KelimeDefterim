package com.example.haznedar.szlk

import com.example.haznedar.kelimedefterim.Database.KelimelerDaoInterface
import com.example.haznedar.market.RetrofitClient

class ApiUtils {

    companion object {

        val BASE_URL = "http://haznedar.de/"

        fun getKelimelerDaoInterface(): KelimelerDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(KelimelerDaoInterface::class.java)

        }
    }
}