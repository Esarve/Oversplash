package com.sourav.oversplash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.repository.ImageApiRepository
import com.sourav.oversplash.utils.DataWrapper
import timber.log.Timber

class ImageViewModel : ViewModel() {
    private var page = 1
    private var perPage = 10
    private val imageApiRepository: ImageApiRepository by lazy {
        ImageApiRepository(perPage)
    }

    fun getRandomImage() = imageApiRepository.getRandomImages()
    fun getImageList() = imageApiRepository.getImageList(page)
    fun getImageListByTopic(topic:String) = imageApiRepository.getImageListByTopic(topic,page)

    fun getNextPage() {
        page++
        Timber.d("Getting more image with page: $page")
        getImageList()
    }

    fun getNextTopicImagePage(topic: String) {
        page++
        Timber.d("Getting more image with page: $page")
        getImageListByTopic(topic)
    }

    private val _randomPhotoLiveData by lazy {
        imageApiRepository.imageLiveData
    }

    private val _photoListLiveData by lazy {
        imageApiRepository.imageListLiveData
    }

    val randomPhoto: MutableLiveData<DataWrapper<Photo>>
        get() = _randomPhotoLiveData
    val photoList: MutableLiveData<DataWrapper<List<Photo>>>
        get() = _photoListLiveData
}