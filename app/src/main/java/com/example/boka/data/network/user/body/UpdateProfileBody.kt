package com.example.boka.data.network.user.body

import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(
    @SerializedName("favorite_genre_ids")
    val favoriteGenreIds : List<Int>
)

