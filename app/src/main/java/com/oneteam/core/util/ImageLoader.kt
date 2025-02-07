package com.oneteam.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = loadBitmapFromUrl(imageUrl)
            withContext(Dispatchers.Main) {
                bitmap?.let { imageView.setImageBitmap(it) }
            }
        }
    }
    private suspend fun loadBitmapFromUrl(url: String): Bitmap? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}