package com.sourav.oversplash.activity

import android.os.Bundle
import androidx.navigation.navArgs
import com.sourav.oversplash.databinding.ActivityPhotoViewBinding
import com.sourav.oversplash.utils.GlideHelper

class PhotoViewActivity : BaseActivity() {
    private lateinit var binding: ActivityPhotoViewBinding
    val args: PhotoViewActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = args.link
        GlideHelper.loadImage(binding.photoView,imageUrl)
    }
}