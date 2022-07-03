package com.sourav.oversplash.activity.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.databinding.AdapterTopicBinding
import com.sourav.oversplash.utils.GlideHelper

class TopicViewHolder(private val binding: AdapterTopicBinding, private val listener: AdapterOnClickListener): RecyclerView.ViewHolder(binding.root) {
    fun bind(topic: Topic){
        GlideHelper.loadImage(binding.topicBG,topic.cover_photo.urls.thumb)
        binding.tvTopicName.text = topic.title
    }
}