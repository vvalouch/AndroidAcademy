package com.concur.test.imagetricks

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.annotation.WorkerThread
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.security.MessageDigest


/**
 * Created by Vladimir Valouch on 12/11/18.
 */
class ImageLoader(private val context: Context) {
    private val TAG = "ImageLoader"

    val dataSet = arrayOf(
        "https://upload.wikimedia.org/wikipedia/commons/5/58/Large_Pinus_glabra.jpg",
        "https://themostbeautifulplacesineurope.files.wordpress.com/2013/06/dsc_0388.jpg",
        "https://mikarchitect.files.wordpress.com/2010/07/cfw-chr-high-resolution.jpg",
        "https://phxarch.files.wordpress.com/2014/04/rappaport.jpg",
        "https://cdn.history.com/sites/2/2015/09/iStock_000038100826_Large.jpg"
        , "https://rwethereyetmom.com/wp-content/uploads/2016/04/DSC_0107_2.jpg",
        "https://www.worldfortravel.com/wp-content/uploads/2015/10/Arches-National-Park-Utah-US.jpg",
        "https://www.worldfortravel.com/wp-content/uploads/2015/04/Yellowstone-National-Park-US.jpg",
        "https://anotherheader.files.wordpress.com/2010/11/pano_02301.jpg",
        "https://i1.wp.com/switchbackkids.com/wp-content/uploads/2016/02/IMG_6445.jpg?fit=3264%2C2448",
        "https://upload.wikimedia.org/wikipedia/commons/e/ee/Tasikmalaya_Regency_hilly_view_2_-_high_res.jpg",
        "https://car-images.bauersecure.com/pagefiles/73784/bmw_x3_2017_11.jpg",
        "https://images.freeimages.com/images/large-previews/5c6/sunset-jungle-1383333.jpg",
        "https://images.freeimages.com/images/large-previews/e4a/surf-3-1378236.jpg",
        "https://images.freeimages.com/images/large-previews/e82/beach-surfing-buggy-s-1462290.jpg",
        "https://images.freeimages.com/images/large-previews/3ff/pray-for-wave-1469024.jpg",
        "https://images.freeimages.com/images/large-previews/43a/positure-ii-1170594.jpg",
        "https://images.freeimages.com/images/large-previews/525/pen-1311782.jpg",
        "https://images.freeimages.com/images/large-previews/209/pen-drive-1239890.jpg"

    )

    private fun getCall(position: Int): Call {
        val request = Request.Builder().url(dataSet[position]).build()
        val client = OkHttpClient.Builder().build()
        return client.newCall(request)
    }

    fun getBitmapViaCallback(position: Int, callback: (Bitmap?) -> Unit) {
        getCall(position).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "downloadImage failed", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                callback(bitmap)
            }
        })

    }

    suspend fun getBitmapCoroutineWay(position: Int): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            val response = getCall(position).execute()
            response.body?.byteStream().use {
                bitmap = BitmapFactory.decodeStream(it)
            }
        }
        return bitmap
    }

    suspend fun getBitmapCoroutineWayCustomized(position: Int): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, "$position-${dataSet.hashCode()}.jpg")

            if (!file.exists()) {
                val response = getCall(position).execute()
                response.body?.byteStream()?.use { input ->
                    FileOutputStream(file).buffered().use {
                        it.write(input.readBytes())
                    }
                }
            }

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, options)

            val outHeight = options.outHeight
            val outWidth = options.outWidth

            val sampleSize = Math.max(outHeight, outWidth) / 200
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
        }
        return bitmap
    }

    fun setBitmapGlide(position: Int, imageView: ImageView) {
        Glide.with(imageView)
            .load(dataSet[position])
            .placeholder(android.R.drawable.alert_dark_frame)
            .optionalFitCenter()
            .into(imageView);
    }
}