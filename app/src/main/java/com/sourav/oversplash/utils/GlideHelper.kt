package com.sourav.oversplash.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sourav.oversplash.Oversplash

class GlideHelper {
    companion object{
        fun loadImage(imageView: ImageView, url:String, radius:Int){
             Glide.with(Oversplash.instance)
                .load(url)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(radius)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
        }

        fun loadImage(imageView: ImageView, url: String) = Glide.with(Oversplash.instance)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}