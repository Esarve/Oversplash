package com.sourav.oversplash.repository

import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.api.APIClientProvider
import com.sourav.oversplash.api.ResponseHandler
import com.sourav.oversplash.api.UnplashAPIService
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.utils.DataWrapper

class TopicRepository {
    private val apiCaller = APIClientProvider()
    private val _topicLiveData = MutableLiveData<DataWrapper<List<Topic>>>()

    fun getTopicList(){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getTopicList(), object : ResponseHandler<List<Topic>>{
            override fun onSuccess(data: List<Topic>) {
                _topicLiveData.value = DataWrapper.success(data)
            }

            override fun onFailure(throwable: Throwable) {
                _topicLiveData.value = DataWrapper.failure(null)
            }

            override fun onError(httpCode: Int) {
                _topicLiveData.value = DataWrapper.errorWithCode(Constants.ERROR,null, httpCode)
            }

        })
    }

    val topicLiveData : MutableLiveData<DataWrapper<List<Topic>>>
        get() = _topicLiveData
}