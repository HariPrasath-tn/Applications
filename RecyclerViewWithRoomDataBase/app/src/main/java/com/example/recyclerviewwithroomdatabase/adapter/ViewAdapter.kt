package com.example.recyclerviewwithroomdatabase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewwithroomdatabase.R

class ViewAdapter(private val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list:List<String> = listOf("")
        set(value){
        field = value
        notifyDataSetChanged()
    }
    /**
     * @Override method of the RecyclerView.Adapter class
     *
     * OnCreate View is the method that create the object of each view and returns it to the recycler View
     *
     * Methodology:
     *      returning the object of the viewHolder to create the view for each element in the list
     *      to show in the recycler view
     *
     *      viewHolder class Constructor takes the argument View (here which is the recycler_view_item layout)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_view_item,
                parent,
                false
            )
        )
    }

    /**
     * @Override method of the RecyclerView.Adapter class
     *
     * onBindViewHolder is the method that binds the viewHolder with the data that is given in the
     * form of list to the recyclerView
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.myTextView.text = list[position]
        }
    }

    /**
     * @Override method of the RecyclerView.Adapter class
     *
     * getItemCount is the method that returns the size of the list given as the input to the
     * RecyclerView
     */
    override fun getItemCount(): Int = list.size

    /**
     * View Holder is the inner class of the class Adapter that Extends the class RecyclerView.ViewHolder
     * and it's Constructor takes the argument View (which is the xml layout of each item)
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val myTextView: TextView = view.findViewById(R.id.myTextView)
    }
}