package com.concur.test.imagetricks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageLoader = ImageLoader(applicationContext)


        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter(imageLoader)
        }
    }


}
