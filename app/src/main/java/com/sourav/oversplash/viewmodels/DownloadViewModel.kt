package com.sourav.oversplash.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.utils.Constants
import com.sourav.oversplash.utils.DataWrapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection


class DownloadViewModel(application: Application) : AndroidViewModel(application) {

    private val _downloadMLD: MutableLiveData<DataWrapper<Uri>> by lazy {
        MutableLiveData<DataWrapper<Uri>>()
    }

    val downloadMLD: MutableLiveData<DataWrapper<Uri>>
        get() = _downloadMLD

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    fun downloadPhoto(photo: Photo){
        _downloadMLD.value = DataWrapper.loading(null)
        disposable.add(
            Single.create { emitter: SingleEmitter<Uri>? ->
                downloadImage(emitter,photo)
            }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { uri: Uri? ->
                   doOnSuccess(uri!!)
                }
                .doOnError { throwable: Throwable? ->
                    doOnFailure()
                }
                .subscribe()
        )
    }

    private fun doOnFailure() {
        _downloadMLD.value = DataWrapper.failure(null)
    }

    private fun doOnSuccess(uri: Uri) {
        _downloadMLD.value  = DataWrapper.success(uri)
    }

    private fun downloadImage(emitter: SingleEmitter<Uri>?,photo: Photo){
        var inputStream: InputStream? = null
        val photoLink = photo.urls.full
        try {
            val url = URL(photoLink)
            val urlConn: URLConnection = url.openConnection()
            val httpConn: HttpURLConnection = urlConn as HttpURLConnection
            httpConn.connect()
            inputStream = httpConn.inputStream
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            emitter?.onError(e)
        } catch (e: IOException) {
            e.printStackTrace()
            emitter?.onError(e)
        }
        val bmpimg = BitmapFactory.decodeStream(inputStream)

        saveImage(bmpimg, photo.id, emitter)
    }

    @Throws(IOException::class)
    private fun saveImage(bitmap: Bitmap, name: String, emitter: SingleEmitter<Uri>?) {
        var saved = false
        var fos: OutputStream? = null
        val imageUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = Oversplash.instance.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, Constants.MIMETYPE_IMAGE)
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/${Constants.DOWNLOAD_FOLDER}")
            imageUri=
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let {
                fos = resolver.openOutputStream(imageUri)
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ).toString() + File.separator + Constants.DOWNLOAD_FOLDER
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, "$name.png")
            fos = FileOutputStream(image)
            imageUri = image.toUri()
        }
        fos?.let {
            saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos?.flush()
            fos?.close()
        }

        when (saved){
            true -> emitter?.onSuccess(imageUri!!)
            else -> emitter?.onError(Throwable())
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}