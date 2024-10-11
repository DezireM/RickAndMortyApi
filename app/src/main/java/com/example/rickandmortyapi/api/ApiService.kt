package com.example.rickandmortyapi.api

import com.example.rickandmortyapi.network.model.BaseResponse
import com.google.android.gms.awareness.snapshot.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("character")
    fun getAllCharacters(): Call<BaseResponse>


    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>



}