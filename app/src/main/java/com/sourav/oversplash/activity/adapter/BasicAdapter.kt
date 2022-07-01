package com.sourav.oversplash.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.AdapterOnClickListener
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.BasicAdapterBinding
import com.sourav.oversplash.utils.GlideHelper

class BasicAdapter(private var photoList: MutableList<Photo>?, private var listener: AdapterOnClickListener): RecyclerView.Adapter<BasicAdapter.ViewHolder>() {

    class ViewHolder(private val binding: BasicAdapterBinding, val listener: AdapterOnClickListener): RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo){
            GlideHelper.loadImage(binding.image,photo.urls.thumb)
            binding.image.setOnClickListener { listener.onClick(photo.urls.full) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = BasicAdapterBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoList?.get(position)
        photo?.let {
            with(holder) { bind(photo = photo) }
        }

    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    fun setData(data: List<Photo>){
        photoList!!.addAll(data)
        notifyDataSetChanged()
    }
}