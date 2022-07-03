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
import androidx.lifecycle.AndroidViewModel
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.data.photo.Photo
import com.sourav.oversplash.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection


class DownloadViewModel(application: Application) : AndroidViewModel(application) {

    val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    fun downloadPhoto(photo: Photo){
        disposable.add(
            Completable.defer {
                Completable.create { emitter: CompletableEmitter? ->
                    downloadImage(emitter, photo)
                }
            }
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doOnSuccess()
                }) { throwable ->
                    doOnFailure()
                    throwable.printStackTrace()
                }
        )
    }

    private fun doOnFailure() {
        TODO("Not yet implemented")
    }

    private fun doOnSuccess() {
        TODO("Not yet implemented")
    }

    private fun downloadImage(emitter: CompletableEmitter?,photo: Photo){
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
    private fun saveImage(bitmap: Bitmap, name: String, emitter: CompletableEmitter?) {
        var saved = false
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = Oversplash.instance.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, Constants.MIMETYPE_IMAGE)
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/${Constants.DOWNLOAD_FOLDER}")
            val imageUri: Uri? =
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
        }
        fos?.let {
            saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos?.flush()
            fos?.close()
        }

        when (saved){
            true -> emitter?.onComplete()
            else -> emitter?.onError(Throwable())
        }

    }
}