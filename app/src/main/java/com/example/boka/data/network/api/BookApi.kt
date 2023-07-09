package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.network.book.body.ReviewBookBody
import com.example.boka.data.network.book.result.BookJson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("books")
    suspend fun getBooks(@Query("isbns") isbns : String) : Response<BodyResult<List<BookJson>>>
    @GET("books/{bookId}")
    suspend fun getBookDetail(@Path("bookId") bookId : Int) : Response<BodyResult<BookJson>>
    @GET("books/top_rated")
    suspend fun getTopRatedBooks() : Response<BodyResult<List<BookJson>>>
    @GET("books/{bookId}/related")
    suspend fun getContentBasedBook(@Path("bookId") bookId : Int) : Response<BodyResult<List<BookJson>>>
    @GET("books/{bookId}/item_based")
    suspend fun getItemBasedBook(@Path("bookId") bookId : Int) : Response<BodyResult<List<BookJson>>>
    @GET("books/recently_viewed")
    suspend fun getRecentlyViewedBooks() : Response<BodyResult<List<BookJson>>>
    @GET("new_user_books")
    suspend fun getNewUserBooks() : Response<BodyResult<List<BookJson>>>
    @POST("book_ratings")
    suspend fun rateBook(@Body reviewBookBody: ReviewBookBody) : Response<BodyResult<Any>>

}