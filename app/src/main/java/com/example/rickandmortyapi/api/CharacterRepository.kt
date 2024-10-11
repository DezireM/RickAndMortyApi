package com.example.rickandmortyapi.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapi.network.model.BaseResponse
import com.example.rickandmortyapi.network.model.Location
import com.example.rickandmortyapi.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val apiService: ApiService) {

    fun getAllCharacters(): LiveData<Resource<List<Character>>> {
        val data = MutableLiveData<Resource<List<Character>>>()

        data.postValue(Resource.Loading())

        apiService.getAllCharacters().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                data.postValue(Resource.Success(response.body()!!.characters))
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                data.postValue(Resource.Error(t.message ?: "Unknown Error"))
            }
        })
        return data
    }

    fun getCharactersById(id: Int): LiveData<Resource<Character>> {
        val data = MutableLiveData<Resource<Character>>()

        data.postValue(Resource.Loading())

        apiService.getCharacterById(id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                data.postValue(Resource.Success(response.body()!!))
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                data.postValue(Resource.Error(t.message ?: "Unknown Error"))
            }
        })
        return data
    }
}