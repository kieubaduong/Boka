package com.example.boka.data.model

data class Book(
    val id : Int = 0,
    val isbn : String = "",
    val title : String,
    val description : String = "",
    val author : String = "",
    val yearOfPublication : Int = 0,
    val publisher : String = "",
    val rating : Double,
    val category: String,
    val tags : List<String> = emptyList(),
    val imageS : String = "",
    val imageM : String = "",
    val imageL : String = "",
){
    companion object{
        val NULL = Book(
            title = "",
            rating = 0.0,
            category = ""
        )
    }
}
