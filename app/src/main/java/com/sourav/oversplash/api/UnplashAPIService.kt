package com.sourav.oversplash.api

import com.sourav.oversplash.data.photo.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UnplashAPIService {

    @GET("/photos/random")
    fun getRandomPhotos():Call<Photo>

    @GET("/photos")
    fun getPhotoList(@QueryMap queryMap: Map<String, Int>): Call<List<Photo>>
}