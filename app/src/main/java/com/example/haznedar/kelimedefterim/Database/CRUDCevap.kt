package com.example.haznedar.kelimedefterim.Database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CRUDCevap(
    @SerializedName("success")
    @Expose
    var success: Int,

    @SerializedName("userid")
    @Expose
    var kullaniciid: String,

    @SerializedName("message")
    @Expose
    var message: String
)

