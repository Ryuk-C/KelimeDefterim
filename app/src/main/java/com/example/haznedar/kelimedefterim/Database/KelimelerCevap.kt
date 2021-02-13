package com.example.haznedar.kelimedefterim.Database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KelimelerCevap(@SerializedName("KelimelerJSON")
                          @Expose
                          var kelimeler: List<Kelimeler>,

                          @SerializedName("success")
                          @Expose
                          var success: Int) {
}