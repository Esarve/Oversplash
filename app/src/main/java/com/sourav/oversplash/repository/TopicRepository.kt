package com.sourav.oversplash.repository

import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.api.APIClientProvider
import com.sourav.oversplash.api.ResponseHandler
import com.sourav.oversplash.api.UnplashAPIService
import com.sourav.oversplash.data.topics.Topic

class TopicRepository {
    private val apiCaller = APIClientProvider()
    private val _topicLiveData = MutableLiveData<List<Topic>>()

    fun getTopicList(){
        apiCaller.doAPICall(apiCaller.getAPIClient(UnplashAPIService::class.java).getTopicList(), object : ResponseHandler<List<Topic>>{
            override fun onSuccess(data: List<Topic>) {
                _topicLiveData.value = data
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }

            override fun onError() {
                TODO("Not yet implemented")
            }

        })
    }

    val topicLiveData : MutableLiveData<List<Topic>>
        get() = _topicLiveData
}