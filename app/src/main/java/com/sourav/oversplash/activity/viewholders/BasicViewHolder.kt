package com.sourav.oversplash.activity.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.BasicAdapterBinding
import com.sourav.oversplash.utils.GlideHelper

class BasicViewHolder (private val binding: BasicAdapterBinding, private val listener: AdapterOnClickListener): RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo){
        GlideHelper.loadImage(binding.image, photo.urls.small, 20)
        binding.image.setOnClickListener { listener.onClick(photo.urls.full) }
    }
}