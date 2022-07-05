package com.sourav.oversplash.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.R
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.databinding.ActivityPhotoViewBinding
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.utils.DataWrapper
import com.sourav.oversplash.utils.GlideHelper
import com.sourav.oversplash.viewmodels.DownloadViewModel

class PhotoViewActivity : BaseActivity() {

    private lateinit var binding: ActivityPhotoViewBinding
    private val args: PhotoViewActivityArgs by navArgs()
    private val downloadViewModl: DownloadViewModel by viewModels()
    private var isShare:Boolean = false;

    private val alertDialog: AlertDialog by lazy {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.dialog_loading);
        builder.create();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photo = args.photo!!
        initView(photo)
        initListener();

        val imageUrl = photo.urls.full
        GlideHelper.loadImage(binding.photoView,imageUrl, object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Toast.makeText(this@PhotoViewActivity, getString(R.string.failed_load_img), Toast.LENGTH_SHORT).show()
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

    private fun initListener() {
        downloadViewModl.downloadMLD.observe(this) {
            when (it.status) {
                DataWrapper.Status.SUCCESS -> {
                    alertDialog.hide()
                    alertDialog.dismiss()
                    if (isShare){
                        initShare(it.data!!)
                    }else{
                        Toast.makeText(this, getString(R.string.dload_success), Toast.LENGTH_SHORT).show()
                    }
                }
                DataWrapper.Status.ERROR ,DataWrapper.Status.FAILURE -> {
                    alertDialog.hide()
                    alertDialog.dismiss()
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                DataWrapper.Status.LOADING -> {
                    alertDialog.show()
                }
            }
        }
    }

    private fun initView(photo: Photo) {
        binding.download.setOnClickListener{
            downloadViewModl.downloadPhoto(photo)
        }

        binding.share.setOnClickListener{
            isShare = true
            downloadViewModl.downloadPhoto(photo)
        }
    }

    private fun initShare(uri: Uri) {
        isShare = false
        val intent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
            type = Constants.MIMETYPE_IMAGE
        }

        val chooserTitle: String = getString(R.string.share_photo)
        val chooser = Intent.createChooser(intent, chooserTitle)
        val resInfoList: List<ResolveInfo> = Oversplash.instance.packageManager.queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName: String = resolveInfo.activityInfo.packageName
            Oversplash.instance.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        shareResult.launch(chooser)
    }

    private val shareResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        //IDK what happens here
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}