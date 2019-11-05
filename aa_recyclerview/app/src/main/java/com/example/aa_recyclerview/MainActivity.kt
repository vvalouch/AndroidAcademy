package com.example.aa_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val records = ArrayList<Record>()
    private val recordAdapter = RecordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        records.add(Record("123"))

        my_button.setOnClickListener {
            records.add(Record(my_input.text.toString()))
            recordAdapter.update(records)
        }

        my_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recordAdapter
        }

        recordAdapter.update(records)


    }
}
