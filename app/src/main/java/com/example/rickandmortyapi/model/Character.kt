package com.example.rickandmortyapi.model

import com.google.gson.annotations.SerializedName

data class Character(

    val id: Int,

    val name: String,

    val status: String,

    val species: String,

    val origin: Origin,

    val image: String,

    val episodes: List<String>
)


data class Origin(
    @SerializedName("name")
    val locationName: String,

    @SerializedName("url")
    val locationUrl: String
)
