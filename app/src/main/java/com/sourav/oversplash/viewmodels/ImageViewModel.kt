package com.sourav.oversplash.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.repository.ImageApiRepository

class ImageViewModel : ViewModel() {
    private val imageApiRepository: ImageApiRepository by lazy {
        ImageApiRepository()
    }
    init {
        imageApiRepository.getRandomImages()
    }

    private val _randomPhotoLiveData by lazy {
        imageApiRepository.imageViewModel
    }

    val randomPhoto: LiveData<Photo> = _randomPhotoLiveData
}