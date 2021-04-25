package com.example.haznedar.kelimedefterim.Translate

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.squareup.okhttp.*
import java.io.IOException


class Translate(var gelenDil: String, var hedefDil: String, var ceviriMetni: String) :
    AsyncTask<String, Void, String>() {

    var url = HttpUrl.Builder()
        .scheme("https")
        .host("api.cognitive.microsofttranslator.com")
        .addPathSegment("/translate")
        .addQueryParameter("api-version", "3.0")
        .addQueryParameter("from", "$gelenDil")
        .addQueryParameter("to", "$hedefDil")
        .build()

    var client = OkHttpClient()

    @Throws(IOException::class)
    fun Post(): String {
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            "[{\"Text\":\" $ceviriMetni\"}]"
        )
        val request = Request.Builder().url(url).post(body)
            .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
            .addHeader("Ocp-Apim-Subscription-Region", location)
            .addHeader("Content-type", "application/json")
            .build()
        val response = client.newCall(request).execute()
        return response.body().string()
    }

    companion object {
        private const val subscriptionKey = "13d86665e039453791755a0480ee893c"

        private const val location = "global"

        fun prettify(json_text: String?): String {
            val parser = JsonParser()
            val json = parser.parse(json_text)
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(json)
        }

        val v11 = "tr"
        val v22 = "en"
        val v33 = "Seni seviyorum"

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                val translateRequest = Translate(v11, v22, v33)
                val response = translateRequest.Post()
                println(prettify(response))
            } catch (e: Exception) {
                println(e)
            }
        }

    }

    override fun doInBackground(vararg params: String?): String {
        TODO("Not yet implemented")
    }


}