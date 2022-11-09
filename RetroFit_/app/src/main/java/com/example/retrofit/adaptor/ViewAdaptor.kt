package com.example.retrofit.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.data_classes.ViewDataClass

class ViewAdaptor(private val context: Context, private val items:ArrayList<ViewDataClass>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_view_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.textView.text = items[position].name
        }

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val textView:TextView = view.findViewById(R.id.myTextView)
        val cardView:CardView = view.findViewById(R.id.myCardView)
    }
}