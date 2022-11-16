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
    /**
     * [onCreateViewHolder] is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      creates the instance of the viewHolder class
     *      finally ==> returns the object of the class ViewHolder
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    /**
     * [onBindViewHolder] is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      -> binds the list of items with the viewHolders
     *      -> here holder's are verified for the user defined ViewHolder class
     *      as the parameter is of increased scope
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            // binding the name of the character with the text view
            holder.textView.text = items[position].name
            // loading the image with the image url
            holder.imageView.load(items[position].image){
                transformations(CircleCropTransformation())
            }
        }
    }

    /**
     * [getItemCount] is an override method of the class RecyclerView.Adapter
     *
     * returns the count of the elements in the items list
     */
    override fun getItemCount(): Int = items.size

    /**
     * class [ViewHolder] is an user defined class that extends the ViewHolder class of RecyclerView
     *  Constructor of this class takes object of the class View
     * Methodology:
     *      it binds layout components with the instance variable
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        // creating the variables for each of the view
        val textView:TextView = view.findViewById(R.id.myTextView)
        val imageView:ImageView = view.findViewById(R.id.myImageView)
        val cardView:CardView = view.findViewById(R.id.myCardView)
    }
}