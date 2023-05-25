package com.example.bookreadingapplication.data.model

data class Book(
    val isbn : String? = null,
    val title : String? = null,
    val subtitle : String? = null,
    val author : String? = null,
    val yearOfPublication : Int? = null,
    val publisher : String? = null,
    val rating : Int? = null,
    val category: String? = null,
)
