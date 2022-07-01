package com.sourav.oversplash.activity

import android.os.Bundle
import com.sourav.oversplash.databinding.ActivityPhotoViewBinding
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.utils.GlideHelper

class PhotoViewActivity : BaseActivity() {
    private lateinit var binding: ActivityPhotoViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageURL : String? = intent.getStringExtra(Constants.IntentKey_PHOTO_URL)
        GlideHelper.loadImage(binding.photoView,imageURL?:"")
    }
}