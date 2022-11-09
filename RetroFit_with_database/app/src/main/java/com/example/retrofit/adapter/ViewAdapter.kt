package com.example.retrofit.adapter

import android.content.Context
import android.transition.CircularPropagation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.retrofit.R
import com.example.retrofit.database.AnimeCharacter

/**
 *  ViewAdapter is an user defined adapter class that extends RecyclerView's Adapter class
 *
 *  info:
 *      Creates and bind the item view for the recycler view and return it to the Recyclerview
 *      with the layout we designed and provided
 *
 *  Methodology:
 *      -> it get the list of elements from the the Activity/Fragment and assign it to the
 *      instance variable items
 *      -> then it counts the number of items in the list (i.e items --> instance variable)
 *      -> Then it creates view for for the list of items(using View holder class) and return it for binding
 *      -> once the list creation is completed the values in the list of the elements are binded and
 *      returned to the recycler view
 *
 */
class ViewAdapter(private val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    /**
     * items is the instance variable that stores the list of items from the activity/fragment
     */
    var items:List<AnimeCharacter> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * onCreate is an override method of the class RecyclerView.Adapter
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
     * onBindViewHolder is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      binds the list of items with the viewHolders
     *
     *      here holder's are verified for the user defined ViewHolder class
     *      as the parameter is of increased scope
     *
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.textView.text = items[position].name
            holder.imageView.load(items[position].image){
                transformations(CircleCropTransformation())
            }
        }
    }

    /**
     * getItemCount is an override method of the class RecyclerView.Adapter
     *
     * returns the count of the elements in the items list
     */
    override fun getItemCount(): Int = items.size

    /**
     * class ViewHolder is an user defined class that extends the ViewHolder class of RecyclerView
     *  Constructor of this class takes object of the class View
     * Methodology:
     *      it binds layout components with the instance variable
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView:TextView = view.findViewById(R.id.myTextView)
        val imageView:ImageView = view.findViewById(R.id.myImageView)
    }
}