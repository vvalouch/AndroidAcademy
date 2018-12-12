package com.concur.test.imagetricks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    /**
     * Android Academy Goals:
    - provide sample app
    - store large image
    - load big image
    - load big image but checking the size before loading
    - load big image which is scaled down
    - use cache
    - bitmap vs byte[] cache
    - different bitmap config
    - usage of matrix operations
    - usage of Exif for correct rotation resolution
     */

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
