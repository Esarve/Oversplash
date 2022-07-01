package com.sourav.oversplash.repository

import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.api.APIClientProvider
import com.sourav.oversplash.api.ResponseHandler
import com.sourav.oversplash.api.UnplashAPIService
import com.sourav.oversplash.data.photo.Photo

class ImageApiRepository {
    private val apiCaller = APIClientProvider()
    private val _imageLiveData = MutableLiveData<Photo>()
    private val _imageListLiveData = MutableLiveData<List<Photo>>()
    val imageLiveData: MutableLiveData<Photo>
        get() = _imageLiveData
    val imageListLiveData: MutableLiveData<List<Photo>>
        get() = _imageListLiveData

    fun getRandomImages(){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getRandomPhotos(), object:ResponseHandler<Photo>{
            override fun onSuccess(data: Photo) {
                _imageLiveData.value = data
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }

            override fun onError() {
                TODO("Not yet implemented")
            }

        } )
    }

    fun getImageList(map: Map<String, Int>?){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getPhotoList(map?: emptyMap()),object:ResponseHandler<List<Photo>>{
            override fun onSuccess(data: List<Photo>) {
                _imageListLiveData.value = data
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }

            override fun onError() {
                TODO("Not yet implemented")
            }

        })
    }

}