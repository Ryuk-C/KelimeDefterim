package com.example.haznedar.kelimedefterim.Database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Diller(@SerializedName("KullaniciDilID")
                  @Expose var dil_id: Int,
                  @SerializedName("DilAd")
                  @Expose var dil_isim: String,
                  @SerializedName("Icon")
                  @Expose var dil_resim: String
) : Serializable {
}