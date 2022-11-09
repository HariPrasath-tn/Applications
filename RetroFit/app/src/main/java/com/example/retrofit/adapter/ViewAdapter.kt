package com.example.retrofit.adapter

import android.content.Context
import android.transition.CircularPropagation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.retrofit.R
import com.example.retrofit.network.Character

class ViewAdapter(private val context:Context, private val items:List<Character>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.textView.text = items[position].name
            holder.imageView.load(items[position].image){
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView:TextView = view.findViewById(R.id.myTextView)
        val imageView:ImageView = view.findViewById(R.id.myImageView)
        val cardView:CardView = view.findViewById(R.id.myCardView)
    }
}