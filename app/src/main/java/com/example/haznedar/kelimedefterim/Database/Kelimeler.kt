package com.example.haznedar.kelimedefterim.Database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Kelimeler(@SerializedName("KelimeID")
                     @Expose var kelime_id: Int,

                     @SerializedName("kelime_dil")
                     @Expose var kelime_dil: String,

                     @SerializedName("AnaKelime")
                     @Expose var ana_kelime: String,

                     @SerializedName("KarsilikKelime")
                     @Expose var kelime_karsilik: String,

                     @SerializedName("OrnekCumle")
                     @Expose var cumle: String

) : Serializable {
}