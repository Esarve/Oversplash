package com.sourav.oversplash.api

import com.sourav.oversplash.data.photo.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnplashAPIService {

    @GET("/photos/random")
    fun getRandomPhotos():Call<Photo>

    @GET("/photos")
    fun getPhotoList(@Query("page") page: Int?,
                     @Query("per_page") per_page: Int?): Call<List<Photo>>
}