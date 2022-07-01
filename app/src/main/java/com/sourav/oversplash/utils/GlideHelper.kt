package com.sourav.oversplash.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sourav.oversplash.Oversplash

class GlideHelper {
    companion object{
        fun loadImage(imageView: ImageView, url:String){
            Glide.with(Oversplash.applicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
        }
    }
}