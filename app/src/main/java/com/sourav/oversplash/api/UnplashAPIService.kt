package com.sourav.oversplash.api

import com.sourav.oversplash.data.photo.Photo
import retrofit2.http.GET

interface UnplashAPIService {

    @GET("/photos/random")
    fun getRandomPhotos():List<Photo>
}