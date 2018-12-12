package com.concur.test.imagetricks

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Created by Vladimir Valouch on 12/11/18.
 */
class ImageLoader(private val context: Context) {

    val dataSet = arrayOf(
        "https://upload.wikimedia.org/wikipedia/commons/5/58/Large_Pinus_glabra.jpg",
        "https://themostbeautifulplacesineurope.files.wordpress.com/2013/06/dsc_0388.jpg",
        "https://mikarchitect.files.wordpress.com/2010/07/cfw-chr-high-resolution.jpg",
        "https://phxarch.files.wordpress.com/2014/04/rappaport.jpg",
        "https://cdn.history.com/sites/2/2015/09/iStock_000038100826_Large.jpg",
        "https://rwethereyetmom.com/wp-content/uploads/2016/04/DSC_0107_2.jpg",
        "https://www.worldfortravel.com/wp-content/uploads/2015/10/Arches-National-Park-Utah-US.jpg",
        "https://www.worldfortravel.com/wp-content/uploads/2015/04/Yellowstone-National-Park-US.jpg",
        "https://anotherheader.files.wordpress.com/2010/11/pano_02301.jpg",
        "https://i1.wp.com/switchbackkids.com/wp-content/uploads/2016/02/IMG_6445.jpg?fit=3264%2C2448",
        "https://upload.wikimedia.org/wikipedia/commons/e/ee/Tasikmalaya_Regency_hilly_view_2_-_high_res.jpg",
        "https://car-images.bauersecure.com/pagefiles/73784/bmw_x3_2017_11.jpg"
    )

    suspend fun getBitmapBadWay(position: Int): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            val tempFile = File(context.cacheDir,"$position-${dataSet[position].hashCode()}.jpg")
            /*
            if (tempFile.exists()) {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(tempFile.absolutePath, options)

                val imageHeight: Int = options.outHeight
                val imageWidth: Int = options.outWidth

                val scale = Math.max(imageHeight, imageWidth) / 200

                options.inJustDecodeBounds = false
                options.inSampleSize = scale

                bitmap = BitmapFactory.decodeFile(tempFile.absolutePath, options)

            } else {*/
                URL(dataSet[position]).openStream().use { input ->

                    FileOutputStream(tempFile).buffered().use {
                        it.write(input.readBytes())
                    }
                    val options = BitmapFactory.Options()
                    options.inJustDecodeBounds = true
                    BitmapFactory.decodeFile(tempFile.absolutePath, options)

                    val imageHeight: Int = options.outHeight
                    val imageWidth: Int = options.outWidth

                    val scale = 1 //Math.max(imageHeight, imageWidth) / 200

                    options.inJustDecodeBounds = false
                    options.inSampleSize = scale

                    bitmap = BitmapFactory.decodeFile(tempFile.absolutePath, options)
                //}
            }
        }
        return bitmap
    }

    fun setBitmapPicasso(position: Int, imageView: ImageView) {
        Picasso.get()
            .load(dataSet[position])
            .placeholder(android.R.drawable.alert_dark_frame)
            //.fit()
            .resize(200, 200).centerInside()
            .transform(object : Transformation {
                override fun key(): String {
                    return "myCustomTransformation"
                }

                override fun transform(source: Bitmap?): Bitmap {
                    val matrix = Matrix()
                    matrix.postRotate(90f)
                    matrix.postScale(0.1f, 0.1f)
                    val result = Bitmap.createBitmap(source, 0, 0, source!!.width, source!!.height, matrix, true)
                    source.recycle()
                    return result
                }
            })
            .into(imageView)
    }
}