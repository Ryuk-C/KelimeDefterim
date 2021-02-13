package com.example.haznedar.kelimedefterim.Database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DillerCevap(@SerializedName("DillerJSON")
                          @Expose
                          var diller: List<Diller>,

                       @SerializedName("success")
                          @Expose
                          var success: Int) {
}