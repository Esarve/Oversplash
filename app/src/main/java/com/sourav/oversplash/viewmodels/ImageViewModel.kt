package com.sourav.oversplash.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.repository.ImageApiRepository

class ImageViewModel : ViewModel() {
    private val imageApiRepository: ImageApiRepository by lazy {
        ImageApiRepository()
    }

    fun getRandomImage() = imageApiRepository.getRandomImages()
    fun getImageList() = imageApiRepository.getImageList(null)

    private val _randomPhotoLiveData by lazy {
        imageApiRepository.imageLiveData
    }

    private val _photoListLiveData by lazy {
        imageApiRepository.imageListLiveData
    }

    val randomPhoto: LiveData<Photo>
        get() = _randomPhotoLiveData
    val photoList: MutableLiveData<List<Photo>>
        get() = _photoListLiveData
}