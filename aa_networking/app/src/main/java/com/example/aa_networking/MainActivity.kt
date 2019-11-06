package com.example.aa_networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_row.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val myAdapter = PostAdapter()
    private val networkClient = NetworkClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3.0 setup recycler view

        /*my_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = myAdapter
        }*/


        //4.0 enable swipe to refresh
        /*
        swipe_refresh_layout.setOnRefreshListener {
            loadItems()
        }
         */


        //5.0 enable swipe to delete
        /*
        ItemTouchHelper(object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwipe(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(my_recyclerview)
        */


        //6.0 Enables functionality for sending data to server
        /*
        my_add_button.setOnClickListener {
            networkClient.postNew(Post("123123123", "title", "body"), { showToast("post", it) })
        }
        */


        //7.0 Image download. The image is displayed on each row
        //networkClient.downloadImage { myAdapter.image = it }
    }

    override fun onResume() {
        super.onResume()
        //3.1 load items on start
        //loadItems()
    }

    private fun loadItems() {
        swipe_refresh_layout.isRefreshing = true
        networkClient.getAll { success, list ->
            myAdapter.update(list)
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun onSwipe(position: Int) {
        networkClient.delete(
            myAdapter.removeAt(position),
            { showToast("delete", it) })
    }

    private fun showToast(prefix: String, result: Boolean) {
        Toast.makeText(this@MainActivity, "The $prefix result is $result", Toast.LENGTH_LONG).show()
    }
}
