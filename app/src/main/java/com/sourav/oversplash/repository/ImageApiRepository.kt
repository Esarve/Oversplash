package com.sourav.oversplash.repository

import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.api.APIClientProvider
import com.sourav.oversplash.api.ResponseHandler
import com.sourav.oversplash.api.UnplashAPIService
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.utils.DataWrapper

class ImageApiRepository(var perPage:Int) {
    private val apiCaller = APIClientProvider()
    private val _imageLiveData = MutableLiveData<DataWrapper<Photo>>()
    private val _imageListLiveData = MutableLiveData<DataWrapper<List<Photo>>>()
    val imageLiveData: MutableLiveData<DataWrapper<Photo>>
        get() = _imageLiveData
    val imageListLiveData: MutableLiveData<DataWrapper<List<Photo>>>
        get() = _imageListLiveData

    fun getRandomImages(){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getRandomPhotos(), object:ResponseHandler<Photo>{
            override fun onSuccess(data: Photo) {
                _imageLiveData.value = DataWrapper.success(data)
            }

            override fun onFailure(throwable: Throwable) {
                _imageLiveData.value = DataWrapper.failure(null);
            }

            override fun onError(httpCode: Int) {
                _imageLiveData.value = DataWrapper.errorWithCode(Constants.ERROR,null,httpCode)
            }

        } )
    }

    fun getImageList(page:Int){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getPhotoList(page,perPage),object:ResponseHandler<List<Photo>>{
            override fun onSuccess(data: List<Photo>) {
                _imageListLiveData.value = DataWrapper.success(data)
            }

            override fun onFailure(throwable: Throwable) {
                _imageListLiveData.value = DataWrapper.failure(null);
            }

            override fun onError(httpCode: Int) {
                _imageListLiveData.value = DataWrapper.errorWithCode(Constants.ERROR,null,httpCode)
            }

        })
    }

}