package com.example.boka.data.model

data class Book(
    val id : Int = 1,
    val isbn : String = "",
    val title : String,
    val description : String = "",
    val author : String = "",
    val yearOfPublication : Int = 0,
    val publisher : String = "",
    val ratingAvg : Double = 0.0,
    val ratingCount : Int = 0,
    val userRating : Int = 0,
    val category: String,
    val tags : List<String> = emptyList(),
    val imageS : String = "",
    val imageM : String = "",
    val imageL : String = "",
){
    companion object{
        val NULL = Book(
            title = "",
            ratingAvg = 0.0,
            category = "",
            ratingCount = 0,
            userRating = 0
        )
    }
}
