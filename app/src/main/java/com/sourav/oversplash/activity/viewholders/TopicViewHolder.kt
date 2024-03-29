package com.sourav.oversplash.activity.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.databinding.AdapterTopicBinding
import com.sourav.oversplash.utils.GlideHelper

class TopicViewHolder(private val binding: AdapterTopicBinding, private val listener: FeedAdapterOnClickListener<Topic>): RecyclerView.ViewHolder(binding.root) {
    fun bind(topic: Topic){
        GlideHelper.loadImage(binding.topicBG,topic.cover_photo.urls.regular)
        binding.tvTopicName.text = topic.title
        binding.parent.setOnClickListener {
            listener.onClick(topic)
        }
    }
}