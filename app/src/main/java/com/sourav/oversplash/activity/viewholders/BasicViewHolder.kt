package com.sourav.oversplash.activity.viewholders

import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.BasicAdapterBinding
import com.sourav.oversplash.utils.GlideHelper

class BasicViewHolder (private val binding: BasicAdapterBinding, private val listener: FeedAdapterOnClickListener): RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo){
        GlideHelper.loadImage(binding.image, photo.urls.small)
        binding.image.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (120..250).random().toFloat(), Oversplash.instance.resources.displayMetrics).toInt()
        binding.image.setOnClickListener { listener.onClick(photo) }
    }
}