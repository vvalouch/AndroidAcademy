package com.example.aa_networking

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    private val posts = ArrayList<Post>()
    var image: Bitmap? = null

    fun update(input: List<Post>) {
        posts.clear()
        posts.addAll(input)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int): Post {
        val deletedPost = posts.removeAt(position)
        notifyItemRemoved(position)
        return deletedPost
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)

        return MyViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostAdapter.MyViewHolder, position: Int) {
        posts[position].let {
            holder.id.text = it.id
            holder.userId.text = it.userId
            holder.title.text = it.title
            holder.body.text = it.body

            //setting the the image to lines
            holder.image.visibility = View.GONE
            image?.let { bitmap ->
                holder.image.apply {
                    visibility = View.VISIBLE
                    setImageBitmap(bitmap)
                }
            }
        }
    }

    class MyViewHolder(
        val view: View,
        val image: ImageView = view.findViewById(R.id.image),
        val id: TextView = view.findViewById(R.id.id),
        val userId: TextView = view.findViewById(R.id.user_id),
        val title: TextView = view.findViewById(R.id.title),
        val body: TextView = view.findViewById(R.id.body)
    ) : RecyclerView.ViewHolder(view) {

    }
}