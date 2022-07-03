package com.sourav.oversplash.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.activity.viewholders.TopicViewHolder
import com.sourav.oversplash.data.topics.Topic
import com.sourav.oversplash.databinding.AdapterTopicBinding

class TopicAdapter (private var topicList: MutableList<Topic>?, private var listener: AdapterOnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val topicViewHolder = AdapterTopicBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopicViewHolder(topicViewHolder, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = topicList?.get(position)
        topicList?.let {
            with(holder as TopicViewHolder) {
                bind(topic!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return topicList?.size ?: 0
    }

    fun setData(data: List<Topic>) {
        topicList!!.addAll(data)
        notifyDataSetChanged()
    }
}