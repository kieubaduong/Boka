package com.example.boka.data.data_source.network.auth.result

import com.example.boka.data.model.Genre
import com.google.gson.annotations.SerializedName

data class SignInResult(
    val id : Int?,
    val email : String,
    @SerializedName("created_at")
    val createdAt : String?,
    @SerializedName("updated_at")
    val updatedAt : String?,
    val name : String,
    @SerializedName("favorite_genres")
    val favouriteGenres : List<Genre> = emptyList(),
    @SerializedName("legacy_id")
    val legacyId : Int?,
)
