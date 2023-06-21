package com.example.boka.domain.entity

data class BookEntity(
    val isbn : String = "",
    val title : String,
    val description : String = "",
    val author : String = "",
    val yearOfPublication : Int = 0,
    val publisher : String = "",
    val rating : Int,
    val category: String,
    val tags : List<String> = emptyList(),
    val imageS : String = "",
    val imageM : String = "",
    val imageL : String = "",
)
