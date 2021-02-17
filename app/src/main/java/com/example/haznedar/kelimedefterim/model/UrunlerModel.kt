package com.example.haznedar.kelimedefterim.model


import com.google.gson.annotations.SerializedName

data class UrunlerModel(
    @SerializedName("success")
    val success: Int,
    @SerializedName("Urunler")
    val urunler: List<Urunler>
) {
    data class Urunler(
        @SerializedName("favorimode")
        val favorimode: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("urunaciklama")
        val urunaciklama: String,
        @SerializedName("urunad")
        val urunad: String,
        @SerializedName("urunfiyat")
        val urunfiyat: String,
        @SerializedName("urunresim")
        val urunresim: String
    )
}