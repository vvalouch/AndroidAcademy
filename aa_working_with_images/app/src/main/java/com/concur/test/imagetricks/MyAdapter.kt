package com.concur.test.imagetricks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.UiThread
import kotlinx.coroutines.*

/**
 * Created by Vladimir Valouch on 12/11/18.
 */
class MyAdapter(private val imageLoader: ImageLoader) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    class MyViewHolder(val row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {
        var imageView: ImageView
        var textText: TextView

        init {
            imageView = row.findViewById(R.id.row_image)
            textText = row.findViewById(R.id.row_text)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        val myRowView = LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)
        return MyViewHolder(myRowView)
    }

    override fun getItemCount() = imageLoader.dataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //1. test usage of Glide
        //easiestWay(holder, position)

        //2. try to load image our way with callbacks
        //callBackWay(holder, position)

        //3. try to load image our way without need for callbacks
        // * Does not handle the network call cancellation as it is beyond scope of the lesson
        coroutineWay(holder, position)

        //4. try to load image our way without need for callbacks that shows how to play further with image if needed
        // * Does not handle the network call cancellation as it is beyond scope of the lesson
        //coroutineWayCustomized(holder, position)

        //easiestWay(holder, position)
    }

    fun callBackWay(holder: MyViewHolder, position: Int) {
        imageLoader.getBitmapViaCallback(position) {
            it?.let { holder.imageView.post { holder.imageView.setImageBitmap(it) } }
        }
    }

    fun coroutineWay(holder: MyViewHolder, position: Int) {
        uiScope.launch {
            holder.imageView.setImageResource(android.R.drawable.alert_dark_frame)
            val bitmap = imageLoader.getBitmapCoroutineWay(position)
            holder.imageView.setImageBitmap(bitmap)
        }
    }

    fun coroutineWayCustomized(holder: MyViewHolder, position: Int) {
        uiScope.launch {
            holder.imageView.setImageResource(android.R.drawable.alert_dark_frame)
            val bitmap = imageLoader.getBitmapCoroutineWayCustomized(position)
            holder.imageView.setImageBitmap(bitmap)
        }
    }

    fun easiestWay(holder: MyViewHolder, position: Int) {
        imageLoader.setBitmapGlide(position, holder.imageView)
    }

}