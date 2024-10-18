package com.example.rickandmortyapi.data.repository

import com.example.rickandmortyapi.data.api.ApiService
import com.example.rickandmortyapi.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class CharacterRepository(
    private val apiService: ApiService
) {

    suspend fun getAllCharacters(): Resource<List<Character>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllCharacters()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.characters)
            } else {
                Resource.Error("Server Error")
            }
        } catch (e: Exception) {
            Resource.Error(handleException(e))
        }
    }

    suspend fun getCharacterById(id: Int): Resource<Character> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCharacterById(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Server Error")
            }
        } catch (e: Exception) {
            Resource.Error(handleException(e))
        }
    }

    private fun handleException(e: Exception): String {
        return when (e) {
            is IOException -> e.localizedMessage ?: "Network Error"
            is HttpException -> e.localizedMessage ?: "Server Error"
            else -> e.localizedMessage ?: "Unknown Error"
        }
    }
}