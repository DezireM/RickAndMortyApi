package com.example.rickandmortyapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.api.CharacterRepository
import com.example.rickandmortyapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters: LiveData<Resource<List<Character>>> get() = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        repository.getAllCharacters().observeForever { characters ->
            _characters.postValue(characters)
        }
    }
}