package com.example.boka.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id : Int,
    val isbn : String,
    val title : String,
    val description: String,
    val price : Int,
    val author : String,
    val publisher : String,
    @SerializedName("year_of_publication")
    val yearOfPublication : Int,
    @SerializedName("image_s")
    val imageS : String,
    @SerializedName("image_m")
    val imageM : String,
    @SerializedName("image_l")
    val imageL : String,
    val tags : List<String>,
    val rating : Int,
    @SerializedName("review_count")
    val reviewCount : Int,
    val genres : List<Genre>,
)
