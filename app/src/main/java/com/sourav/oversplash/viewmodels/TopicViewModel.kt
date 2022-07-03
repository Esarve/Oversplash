package com.sourav.oversplash.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.repository.TopicRepository

class TopicViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TopicRepository by lazy {
        TopicRepository()
    }

    fun getTopics() = repository.getTopicList();

    private val _topicLiveData: MutableLiveData<List<Topic>> by lazy {
        repository.topicLiveData
    }

    val topicLiveData: MutableLiveData<List<Topic>>
        get() = _topicLiveData
}