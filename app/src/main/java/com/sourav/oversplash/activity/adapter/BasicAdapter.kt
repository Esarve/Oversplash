package com.sourav.oversplash.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sourav.oversplash.Interfaces.FeedAdapterOnClickListener
import com.sourav.oversplash.activity.viewholders.BasicViewHolder
import com.sourav.oversplash.activity.viewholders.ProgressHolder
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.BasicAdapterBinding
import com.sourav.oversplash.databinding.ViewholderLoadingBinding

class BasicAdapter(private var photoList: MutableList<Photo>?, private var listener: FeedAdapterOnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DEFAULT: Int = 1
    private val LOADING: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val basicViewHolder = BasicAdapterBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        val loadingBinding = ViewholderLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return if (viewType == DEFAULT) BasicViewHolder(basicViewHolder, listener) else ProgressHolder(loadingBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = photoList?.get(position)
        photo?.let {
            if (holder is BasicViewHolder) {
                with(holder){bind(photo = photo)}
            }
        }

    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            photoList!!.size-1 -> LOADING
            else -> DEFAULT
        }
    }

    fun setData(data: List<Photo>){
        val start = photoList!!.size-1
        photoList!!.addAll(data)
        notifyItemRangeInserted(start,data.size)
    }
}