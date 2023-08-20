package com.example.qrcodepayment.data.API

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

data class Promo(
    val id: String,
    val nama: String,
    val desc: String,
    val lokasi: String,
    val img: PromoImage
)

data class PromoImage(
    val url: String
)

