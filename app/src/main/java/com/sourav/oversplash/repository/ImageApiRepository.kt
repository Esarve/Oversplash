package com.sourav.oversplash.repository

import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.api.APIClientProvider
import com.sourav.oversplash.api.ResponseHandler
import com.sourav.oversplash.api.UnplashAPIService
import com.sourav.oversplash.data.photo.Photo

class ImageApiRepository() {
    private val apiCaller = APIClientProvider()
    private val _imageLiveData = MutableLiveData<Photo>()
    val imageViewModel: MutableLiveData<Photo>
        get() = _imageLiveData

    fun getRandomImages(){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getRandomPhotos(), object:ResponseHandler<Photo>{
            override fun onSuccess(data: Photo) {
                _imageLiveData.postValue(data)
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }

            override fun onError() {
                TODO("Not yet implemented")
            }

        } )
    }

}