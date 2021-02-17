package com.example.haznedar.kelimedefterim.Database

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KelimelerDaoInterface {

    @POST("Haznedar/KelimeDefterim/KelimeListele.php")
    @FormUrlEncoded
    fun tumKelimeler(
        @Field("Pst_Kullanici_Dil_ID") Pst_Dil_ID: Int,
        @Field("Pst_User_ID") Pst_User_ID: Int

        ): Call<KelimelerCevap>

    @POST("Haznedar/KelimeDefterim/DilListele.php")
    @FormUrlEncoded
    fun tumDiller(@Field("Pst_User_ID") Pst_User_ID: String): Call<DillerCevap>

    @POST("Haznedar/KelimeDefterim/KelimeAra.php")
    @FormUrlEncoded
    fun kelimeAra(
        @Field("arananDeger") arananDeger: String,
        @Field("kullaniciDILID") kullaniciDILID: Int
    ): Call<KelimelerCevap>

    @POST("Haznedar/KelimeDefterim/girisYap.php")
    @FormUrlEncoded
    fun kullaniciAra(
        @Field("Pst_EMail") Pst_EMail: String,
        @Field("Pst_Sifre") Pst_Sifre: String
    ): Call<CRUDCevap>

    @POST("Haznedar/KelimeDefterim/insert_kisiler.php")
    @FormUrlEncoded
    fun kullaniciEkle(
        @Field("Pst_EMail") Pst_EMail: String,
        @Field("Pst_Sifre") Pst_Sifre: String
    ): Call<CRUDCevap>

    @POST("Haznedar/KelimeDefterim/insert_kelimeler.php")
    @FormUrlEncoded
    fun kelimeEkle(
        @Field("Pst_DilID") Pst_DilID: Int,
        @Field("Pst_Kelime") Pst_Kelime: String,
        @Field("Pst_Karsilik") Pst_Karsilik: String,
        @Field("Pst_Cumle") Pst_Cumle: String
    ): Call<CRUDCevap>

    @POST("Haznedar/KelimeDefterim/delete_kelimeler.php")
    @FormUrlEncoded
    fun kelimeSil(
        @Field("Pst_Kelime_ID") Pst_Kelime_ID: Int,
    ): Call<CRUDCevap>
}