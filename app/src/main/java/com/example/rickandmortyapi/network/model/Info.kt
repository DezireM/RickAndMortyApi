package com.example.rickandmortyapi.network.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: String? = null
)