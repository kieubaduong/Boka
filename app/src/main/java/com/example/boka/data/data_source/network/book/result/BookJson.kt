package com.example.boka.data.data_source.network.book.result

import com.example.boka.data.model.Book
import com.example.boka.data.model.Genre
import com.google.gson.annotations.SerializedName

data class BookJson(
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
    val rating : Double,
    @SerializedName("review_count")
    val reviewCount : Int,
    val genres : List<Genre>,
){
    fun toBook(): Book {
        val category = genres.joinToString { it.name }
        return Book(
            isbn = isbn,
            title = title,
            description = description,
            author = author,
            yearOfPublication = yearOfPublication,
            publisher = publisher,
            rating = rating,
            category = category,
            tags = tags,
            imageS = imageS,
            imageM = imageM,
            imageL = imageL
        )
    }
}
