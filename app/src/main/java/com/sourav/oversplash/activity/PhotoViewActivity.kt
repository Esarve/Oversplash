package com.sourav.oversplash.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.ActivityPhotoViewBinding
import com.sourav.oversplash.utils.GlideHelper
import com.sourav.oversplash.viewmodels.DownloadViewModel

class PhotoViewActivity : BaseActivity() {

    private lateinit var binding: ActivityPhotoViewBinding
    val args: PhotoViewActivityArgs by navArgs()
    private val downloadViewModl: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photo = args.photo!!
        initView(photo)

        val imageUrl = photo.urls.full
        GlideHelper.loadImage(binding.photoView,imageUrl, object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Toast.makeText(this@PhotoViewActivity, "Failed To Load Image", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                binding.progressBar.visibility = View.GONE
                return false
            }

        })
    }

    private fun initView(photo: Photo) {
        binding.download.setOnClickListener{
            downloadViewModl.downloadPhoto(photo)
        }
    }
}