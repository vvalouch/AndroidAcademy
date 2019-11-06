package com.example.aa_networking

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException


/**
 * Check https://square.github.io/retrofit/ for more info on Retrofit library
 * The code below is handling just happy path for sake of simplicity.
 */
class NetworkClient {
    private val TAG = NetworkClient::class.java.simpleName


    //3.1 creates all logging http client
    /*
    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor();
        logging.level = HttpLoggingInterceptor.Level.BODY;
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();
    }*/

    //3.2 create function that instantiates service based on your interface
    /*private fun getRetrofit(): PostInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()

        return retrofit.create(PostInterface::class.java)
    }
*/

    fun getAll(callback: (Boolean, List<Post>) -> Unit) {
        //3.3 enable download of items

        /*
        getRetrofit().doGetCall().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                callback(response.isSuccessful, response.body() ?: listOf())
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "getAll failed", t)
                callback(false, listOf())
            }
        })
        */

    }

    fun delete(post: Post, callback: (Boolean) -> Unit) {
        //5.2 enable delete if the id is available
        /*
        if (post.id.isNullOrBlank()) {
            Log.e(TAG, "delete failed due to null or empty id")
            return
        }

        getRetrofit().doDeleteCall(post.id ?: "0").enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "delete failed", t)
                callback(false)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }
        })

         */
    }

    fun postNew(post: Post, callback: (Boolean) -> Unit) {
        //6.2 handling of sending items to server
        /*
        getRetrofit().doPostCall(post).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, "postNew failed", t)
                callback(false)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Log.e(TAG, "postNew response code ${response.code()}")
                callback(response.isSuccessful)
            }
        })

         */
    }

    fun downloadImage(callback: (Bitmap?) -> Unit) {
        //7.1 example implementation of image download

/*

        val iconUrl = "https://square.github.io/okhttp/images/icon-square.png"
        val request = Request.Builder().url(iconUrl).build()
        getHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e(TAG, "downloadImage failed", e)
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                callback(bitmap)
            }
        })
*/

    }

}


interface PostInterface {

    // 3.1 define loading
    /*@GET("posts")
    fun doGetCall(): Call<List<Post>>*/


    //5.1 define delete
    /*
    @DELETE("posts/{id}")
    fun doDeleteCall(@Path("id") id: String): Call<Void>
     */

    //6.1 define posting of a new item to server
    /*
    @POST("posts")
    fun doPostCall(@Body post: Post): Call<Post>
    */
}