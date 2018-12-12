package com.concur.test.imagetricks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Vladimir Valouch on 12/11/18.
 */
class MyAdapter(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var imageView: ImageView

        init {
            imageView = row.findViewById(R.id.row_image)
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
        uiScope.launch {
            val bitmap = imageLoader.getBitmapBadWay(position)
            holder.imageView.setImageBitmap(bitmap)
        }

        //imageLoader.setBitmapPicasso(position, holder.imageView)
    }
}