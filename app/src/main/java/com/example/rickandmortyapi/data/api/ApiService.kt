package com.example.rickandmortyapi.data.api

import com.example.rickandmortyapi.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<BaseResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>
}