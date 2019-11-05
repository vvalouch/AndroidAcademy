package com.example.aa_recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter :
    RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private val myDataset = ArrayList<Record>()

    public fun update(dataSet: ArrayList<Record>) {
        myDataset.clear()
        myDataset.addAll(dataSet)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.MyViewHolder {
        val layout =
            LayoutInflater.from(parent.rootView.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = myDataset[position].text
    }

    class MyViewHolder(val view: View, val textView: TextView = view.findViewById(R.id.my_text)) :
        RecyclerView.ViewHolder(view)


}