package com.example.rickandmortyapi.repository

import com.example.rickandmortyapi.model.Character
import com.example.rickandmortyapi.network.RetroficClient

class CharacterRepository {

    suspend fun fetchCharacter(characterId: Int): Character? {

        val response = RetroficClient.apiService.getCharacter(characterId)
        return if (response.isSuccessful) {

            response.body()
        } else {
            null
        }

    }


}